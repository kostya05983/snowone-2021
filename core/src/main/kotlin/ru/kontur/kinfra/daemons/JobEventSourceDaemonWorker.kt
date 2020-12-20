package ru.kontur.kinfra.daemons

import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.reactive.asFlow
import ru.kontur.kinfra.events.Event
import ru.kontur.kinfra.events.EventSourceable
import kotlin.Exception

abstract class JobEventSourceDaemonWorker<out E : Event>(
    private val eventSource: EventSourceable<JobEvent<E>>
) : SequentialDaemonWorker() {
    override suspend fun handle() {
        try {
            eventSource.events().doOnComplete {
                logger.info("Flux was completed")
            }.doOnError {
                logger.info("Flux was completed with error $it")
            }.asFlow()
                .buffer(BACKPRESSURE_SIZE)
                .collect { event ->
                    when (handleInternal(event)) {
                        true -> {
                            event.saveState()
                        }
                        false -> {
                            throw AbortFlowException()
                        }
                    }
                }
        } catch (ignored: AbortFlowException) {
        }
    }

    private suspend fun handleInternal(event: JobEvent<*>): Boolean {
        return try {
            event.handle()
            logger.info("Processed event item ${event.id}")
            true
        } catch (ex: Exception) {
            logger.error("Can't processed event item ${event.id}")
            false
        }
    }

    private class AbortFlowException : Exception()

    private companion object {
        const val BACKPRESSURE_SIZE = 10;
    }
}