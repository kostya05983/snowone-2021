package ru.kontur.kinfra.daemons

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.asFlux
import reactor.core.publisher.Flux
import ru.kontur.kinfra.events.Event
import ru.kontur.kinfra.events.EventSourceable

abstract class CompositeEventSourceDaemonWorker(
    eventSources: List<JobEventSource<out Event>>
) : JobEventSourceDaemonWorker<Event>(
    CompositeHandleableEventSource(eventSources)
)

internal class CompositeHandleableEventSource(
    private val eventSources: List<JobEventSource<out Event>>
) : EventSourceable<JobEvent<Event>> {

    override fun events(resumeAfter: String?): Flow<JobEvent<Event>> {
        return Flux.merge(eventSources.map { it.events().asFlux() }).asFlow()
    }
}
