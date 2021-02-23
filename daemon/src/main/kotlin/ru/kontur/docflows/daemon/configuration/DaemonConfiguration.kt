package ru.kontur.docflows.daemon.configuration

import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.messaging.rsocket.RSocketStrategies
import ru.kontur.docflows.api.DocflowsApiClient
import ru.kontur.docflows.api.DocflowsClientImpl
import ru.kontur.docflows.api.dto.events.DocflowEventDto
import ru.kontur.kinfra.client.RSocketEventClient
import ru.kontur.kinfra.events.EventSourceable
import ru.kontur.kinfra.queue.RabbitSyncAsyncQueueFactory
import ru.kontur.kinfra.queue.impl.RabbitConnectionOptions
import ru.kontur.kinfra.queue.impl.RabbitSyncAsyncQueueFactoryImpl
import ru.kontur.kinfra.states.MongoStateStorageImpl
import ru.kontur.kinfra.states.StateStorage
import java.net.URI

@Configuration
class DaemonConfiguration(
    private val connectionOptions: RabbitConnectionOptions
) {
    @Bean
    fun rabbitSyncAsyncQueueFactory(): RabbitSyncAsyncQueueFactory {
        return RabbitSyncAsyncQueueFactoryImpl(
            connectionOptions
        )
    }

    @Bean
    fun docflowsStream(rSocketStrategies: RSocketStrategies): EventSourceable<DocflowEventDto> {
        return RSocketEventClient(
            host = URI.create("http://127.0.0.1:8080"),
            path = "/snow-one/docflows",
            eventType = DocflowEventDto::class,
            route = "docflowEventStream",
            rSocketStrategies = rSocketStrategies
        )
    }

    @Bean
    fun docflowsClient(): DocflowsApiClient {
        return DocflowsClientImpl(
            serverHost = "localhost:8080"
        )
    }

    @Bean
    fun docflowsSourceState(
        template: ReactiveMongoTemplate
    ): StateStorage<String> {
        return MongoStateStorageImpl(
            "docflows-daemon-state",
            MongoClients.create(),
            "snowone"
        )
    }
}