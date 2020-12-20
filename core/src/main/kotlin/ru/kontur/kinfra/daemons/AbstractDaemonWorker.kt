package ru.kontur.kinfra.daemons

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicLong
import kotlin.coroutines.coroutineContext
import kotlin.system.exitProcess

abstract class AbstractDaemonWorker {
    protected val logger = LoggerFactory.getLogger(this::class.java)

    protected val workerName = this::class.simpleName!!.decapitalize()

    protected open val completionHandler: CompletionHandler = { error ->
        when (error) {
            is CancellationException -> logger.info("Job cancellled")
            null -> {
                logger.warn("Job finished")
                exitProcess(0)
            }
            else -> {
                logger.error("error=$error, Job failed")
                exitProcess(1)
            }
        }
    }

    private val daemonDispatcher = Executors.newSingleThreadExecutor { r ->
        Thread(r, "DaemonWorker-${DAEMON_DISPATCHER_INDEX.getAndIncrement()}").apply {
            isDaemon = true
        }
    }.asCoroutineDispatcher()

    private val job = GlobalScope.launch(daemonDispatcher, start = CoroutineStart.LAZY) { run(this) }

    fun start() {
        logger.info("Run $workerName")

        job.start()
        job.invokeOnCompletion(completionHandler)
    }

    protected suspend fun loop(job: suspend () -> Unit) {
        while (coroutineContext.isActive) {
            try {
                job()
            } catch (ex: CancellationException) {
                throw ex
            } catch (ex: Exception) {
                logger.error("Execution exception ${ex.message}")
                delay(ERROR_DELAY)
            }
        }
    }

    protected abstract suspend fun run(scope: CoroutineScope)

    private companion object {
        val DAEMON_DISPATCHER_INDEX = AtomicLong(0)
        const val ERROR_DELAY = 10_000L
    }
}