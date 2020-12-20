package ru.kontur.kinfra.daemons

import kotlinx.coroutines.reactor.mono
import reactor.core.publisher.Flux
import ru.kontur.kinfra.events.Event
import ru.kontur.kinfra.events.EventSourceable
import ru.kontur.kinfra.states.StateStorage

class JobEventSource<E : Event>(
    private val eventSource: EventSourceable<E>,
    private val eventHandler: EventHandler<E>,
    private val stateStorage: StateStorage<String>
) : EventSourceable<JobEvent<E>> {

    override fun events(resumeAfter: String?): Flux<out JobEvent<E>> {
        return mono { StateWrapper(stateStorage.getState()) }.flatMapMany {
            eventSource.events(it.value)
        }.map { JobEvent(it, eventHandler, stateStorage) }
    }

    internal data class StateWrapper(val value: String?)
}