package ru.kontur.docflows.api.model.docflows.events

import ru.kontur.kinfra.events.Action
import ru.kontur.kinfra.events.DatabaseEvent
import ru.kontur.kinfra.events.EntityEvent
import ru.kontur.kinfra.events.EntityType
import ru.kontur.docflows.api.model.UserRole
import java.time.Instant
import java.util.*

data class DocflowEntityEvent(
    override val id: String,
    override val type: EntityType,
    override val entityId: String,
    override val timestamp: Instant,
    override val traceId: String,
    override val actions: List<DocflowAction>
) : EntityEvent<DocflowAction>()

data class DocflowDomainEvent(
    override var traceId: String,
    override var timestamp: Instant,
    override var actions: List<DocflowAction>,
    val correlationId: String
) : DatabaseEvent<DocflowAction>()

sealed class DocflowAction(
    override var eventId: UUID
) : Action()

data class CreateOnTransportAction(
    override var eventId: UUID,
    val name: String
) : DocflowAction(eventId)

data class SendNotificationAction(
    override var eventId: UUID,
    val email: String
) : DocflowAction(eventId)