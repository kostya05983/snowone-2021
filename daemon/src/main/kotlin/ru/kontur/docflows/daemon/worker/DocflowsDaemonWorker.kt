package ru.kontur.docflows.daemon.worker

import org.springframework.stereotype.Component
import ru.kontur.kinfra.daemons.EventHandler
import ru.kontur.kinfra.daemons.EventSourceDaemonWorker
import ru.kontur.kinfra.events.EventSourceable
import ru.kontur.kinfra.states.StateStorage
import ru.kontur.docflows.api.dto.events.DocflowEventDto

@Component
class DocflowsDaemonWorker(
    docflowsSource: EventSourceable<DocflowEventDto>,
    eventHandler: EventHandler<DocflowEventDto>,
    stateStorage: StateStorage<String>
) : EventSourceDaemonWorker<DocflowEventDto>(
    docflowsSource,
    eventHandler,
    stateStorage
)