package ru.kontur.users.api.model.users.events

import ru.kontur.kinfra.Action
import ru.kontur.kinfra.DatabaseEvent
import java.util.*

class UserDomainEvent : DatabaseEvent {

}

sealed class UserAction(
    override var eventId: UUID
) : Action()