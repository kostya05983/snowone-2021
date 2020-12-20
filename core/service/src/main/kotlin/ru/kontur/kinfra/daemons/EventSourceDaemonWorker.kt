package ru.kontur.kinfra.daemons

import ru.kontur.kinfra.events.Event
import ru.kontur.kinfra.events.EventSourceable
import ru.kontur.kinfra.states.StateStorage

abstract class EventSourceDaemonWorker<E : Event>(
    eventSource: EventSourceable<E>,
    eventHandler: EventHandler<E>,
    stateStorage: StateStorage<String>
) : JobEventSourceDaemonWorker<E>(
    JobEventSource(eventSource, eventHandler, stateStorage)
)