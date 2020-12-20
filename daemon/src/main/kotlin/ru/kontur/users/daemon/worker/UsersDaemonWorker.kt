package ru.kontur.users.daemon.worker

import org.springframework.stereotype.Component
import ru.kontur.kinfra.daemons.EventHandler
import ru.kontur.kinfra.daemons.EventSourceDaemonWorker
import ru.kontur.kinfra.events.EventSourceable
import ru.kontur.kinfra.states.StateStorage
import ru.kontur.users.api.dto.events.UserEventDto

@Component
class UsersDaemonWorker(
    usersSource: EventSourceable<UserEventDto>,
    eventHandler: EventHandler<UserEventDto>,
    stateStorage: StateStorage<String>
) : EventSourceDaemonWorker<UserEventDto>(
    usersSource,
    eventHandler,
    stateStorage
)