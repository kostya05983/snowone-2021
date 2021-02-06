package ru.kontur.kinfra.queue.impl

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Address
import com.rabbitmq.client.ConnectionFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.rabbitmq.*
import reactor.util.context.Context
import reactor.util.retry.Retry
import ru.kontur.kinfra.queue.*
import ru.kontur.kinfra.queue.Sender
import java.time.Duration
import java.util.concurrent.TimeoutException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class RabbitSyncAsyncTaskQueue<T : Task>(
    private val connectionOptions: RabbitConnectionOptions,
    queueName: String,
    private val serializer: TaskSerializer<T>
) : TaskQueue<T> {
    protected val workQueue = getQueueName(queueName)
    protected val workExchange = "web.exchange"

    private val connectionFactory = ConnectionFactory().apply {
        username = connectionOptions.username
        password = connectionOptions.password
        virtualHost = connectionOptions.virtualHost
        useNio()
    }

    override fun sender(): Sender<T> {
        return createSender()
    }

    override fun receiver(): SyncReceiver<T> {
        initQueueAutoDelete()
        return createReceiver()
    }

    protected open fun initQueueAutoDelete() {
        createQueue(workQueue, workExchange, workQueue, autoDelete = true)
    }

    protected fun createQueue(
        queue: String,
        exchange: String,
        routingKey: String,
        autoDelete: Boolean = false,
        queueOptions: Map<String, Any>? = null
    ) {
        val senderOptions = senderOptions()

        RabbitFlux.createSender(senderOptions).use { sender ->
            val queueSpecification = getQueueSpecification(queue, autoDelete).apply {
                if (queueOptions != null) {
                    arguments(queueOptions)
                }
            }

            val exchangeSpecification = getExchangeSpecification(exchange)

            sender.declareExchange(exchangeSpecification).block(DEFAULT_TIMEOUT)
            sender.declare(queueSpecification).block(DEFAULT_TIMEOUT)

            val bindingSpecification = BindingSpecification
                .queueBinding(exchange, routingKey, queue)
            sender.bind(bindingSpecification).block(DEFAULT_TIMEOUT)
        }
    }

    private inner class RabbitSender(private val sender: reactor.rabbitmq.Sender) : Sender<T> {
        private val logger = LoggerFactory.getLogger(this::class.java)

        override suspend fun send(task: T, initialDelay: Duration) {
            val taskProperties = AMQP.BasicProperties.Builder()
                .contentType(serializer.contentType)
                .expiration(Long.MAX_VALUE.toString())
                .deliveryMode(2) //durability
                .expiration(TTL_MESSAGE)
                .build()

            val messages = Mono.just(task).map {
                val body = serializer.serialize(it)
                OutboundMessage("", workQueue, taskProperties, body)
            }

            logger.info("Start sending message to queue: $workQueue")

            val publishConfirm = sender.sendWithPublishConfirms(messages).awaitFirst()

            require(publishConfirm.isAck && !publishConfirm.isReturned) {
                "Failed to send task: Ack: ${publishConfirm.isAck}, Returned: ${publishConfirm.isReturned}"
            }
        }

        override fun close() = sender.close()
    }

    private inner class RabbitReceiver(private val receiver: reactor.rabbitmq.Receiver) : SyncReceiver<T> {

        private val logger = LoggerFactory.getLogger(this::class.java)

        @Suppress("TooGenericExceptionCaught")
        override suspend fun await(correlationId: String): OperationResult<T, Throwable> {
            logger.info("Start consuming queue: $workQueue")
            return try {
                receiver.consumeManualAck(workQueue, ConsumeOptions().qos(DEFAULT_QOS))
                    .timeout(RECIEVER_DEFAULT_TIMEOUT)
                    .map { rabbitTask ->
                        val contentType = rabbitTask.properties.contentType
                        require(contentType == serializer.contentType) {
                            "Can't " +
                                "deserialize $contentType"
                        }
                        serializer.deserialize(rabbitTask.body)
                    }.filter {
                        it.correlationId == correlationId
                    }.map { rabbitTask ->
                        OperationResult.success(rabbitTask)
                    }.doOnError { ex ->
                        logger.error("Consumer throw exception $ex")
                        OperationResult.fail(ex)
                    }.awaitFirstOrNull()?.let {
                        logger.info("Received message is: $it ; Type = ${it.javaClass}")
                        it
                    } ?: OperationResult.fail(Throwable("Result was empty"))
            } catch (exc: TimeoutException) {
                logger.info("Rabbitmq async-sync way not working: $exc")
                return OperationResult.fail(exc)
            }
        }

        override fun close() = receiver.close()
    }

    private fun createReceiver(): RabbitReceiver {
        val receiverOptions = ReceiverOptions()
            .connectionFactory(connectionFactory)
            .connectionSupplier { cf ->
                cf.newConnection(connectionOptions.host.map(Address::parseAddress), "reactive-receiver")
            }
            .connectionSubscriptionScheduler(Schedulers.boundedElastic())
            .connectionMonoConfigurator { cm -> cm.retryWhen(Retry.backoff(Long.MAX_VALUE, DEFAULT_RETRY_DELAY)) }

        val receiver = RabbitFlux.createReceiver(receiverOptions)

        return RabbitReceiver(receiver)
    }

    private fun createSender(): RabbitSender {
        val senderOptions = senderOptions()

        val sender = RabbitFlux.createSender(senderOptions)

        return RabbitSender(sender)
    }

    private fun senderOptions(): SenderOptions {
        return SenderOptions()
            .connectionFactory(connectionFactory)
            .connectionSupplier { cf ->
                cf.newConnection(connectionOptions.host.map(Address::parseAddress), "reactive-sender")
            }
            .resourceManagementScheduler(Schedulers.boundedElastic())
            .connectionMonoConfigurator { cm ->
                cm.retryWhen(
                    Retry.backoff(
                        Long.MAX_VALUE,
                        DEFAULT_RETRY_DELAY
                    )
                )
            }
    }

    private fun getQueueSpecification(queueName: String, autoDelete: Boolean) = QueueSpecification
        .queue(queueName)
        .durable(true)
        .exclusive(false)
        .autoDelete(autoDelete)

    private fun getExchangeSpecification(exchange: String) = ExchangeSpecification
        .exchange(exchange)
        .type("direct")
        .durable(true)
        .autoDelete(false)

    private fun getQueueName(name: String): String {
        return "realty.web.$name"
    }

    private fun <T> coroutineToMono(func: suspend CoroutineScope.() -> T?): Mono<T> {
        return Mono.subscriberContext().flatMap { ctx ->
            mono(ctx.toCoroutineContext(), func)
        }
    }

    private fun Context.toCoroutineContext(): CoroutineContext {
        return this.stream()
            .filter { it.value is CoroutineContext }
            .map { it.value as CoroutineContext }
            .reduce { context1, context2 -> context1 + context2 }
            .orElse(EmptyCoroutineContext)
    }


    private companion object {
        val RECIEVER_DEFAULT_TIMEOUT: Duration = Duration.ofSeconds(7)
        val DEFAULT_TIMEOUT: Duration = Duration.ofSeconds(20)
        val DEFAULT_RETRY_DELAY: Duration = Duration.ofMillis(500)
        val TTL_MESSAGE = "30000"
        const val DEFAULT_QOS = 1
    }
}
