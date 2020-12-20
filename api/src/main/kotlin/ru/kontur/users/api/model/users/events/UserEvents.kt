package ru.kontur.users.api.model.users.events

import ru.kontur.kinfra.Action
import ru.kontur.kinfra.DatabaseEvent
import ru.kontur.kinfra.EntityEvent
import ru.kontur.kinfra.EntityType
import ru.kontur.users.api.model.UserRole
import java.time.Instant
import java.util.*

data class UserEntityEvent(
    override val id: String,
    override val type: EntityType,
    override val entityId: String,
    override val timestamp: Instant,
    override val traceId: UUID,
    override val actions: List<UserAction>
) : EntityEvent<UserAction>()

data class UserDomainEvent(
    override var traceId: String,
    override var actions: List<UserAction>
) : DatabaseEvent<UserAction>()

sealed class UserAction(
    override var eventId: UUID
) : Action()

data class GiveAccessAction(
    override var eventId: UUID,
    val role: UserRole
) : UserAction(eventId)

data class SendNotificationAction(
    override var eventId: UUID,
    val email: String
) : UserAction(eventId)