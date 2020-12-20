package ru.kontur.kinfra.daemons

import ru.kontur.kinfra.events.Event
import ru.kontur.kinfra.states.StateStorage

class JobEvent<out E : Event>(
    private val event: E,
    private val eventHandler: EventHandler<E>,
    private val stateStorage: StateStorage<String>
) : Event {
    override val id: String = event.id

    suspend fun handle() {
        eventHandler.handle(event)
    }

    suspend fun saveState() {
        stateStorage.saveState(event.id)
    }
}