package ru.kontur.kinfra.daemons

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.reactor.mono
import ru.kontur.kinfra.events.Event
import ru.kontur.kinfra.events.EventSourceable
import ru.kontur.kinfra.states.StateStorage

class JobEventSource<E : Event>(
    private val eventSource: EventSourceable<E>,
    private val eventHandler: EventHandler<E>,
    private val stateStorage: StateStorage<String>
) : EventSourceable<JobEvent<E>> {

    override fun events(resumeAfter: String?): Flow<JobEvent<E>> {
        return mono { StateWrapper(stateStorage.getState()) }.flatMapMany {
            eventSource.events(it.value).asFlux()
        }.map { JobEvent(it, eventHandler, stateStorage) }.asFlow()
    }

    internal data class StateWrapper(val value: String?)
}