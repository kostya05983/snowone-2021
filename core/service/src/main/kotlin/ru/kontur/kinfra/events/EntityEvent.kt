package ru.kontur.kinfra.events

import java.time.Instant
import java.util.*

abstract class EntityEvent<ACTION : Action> : TypedEntityEvent {
    abstract val entityId: String
    abstract val timestamp: Instant
    abstract val traceId: String
    abstract val actions: List<ACTION>
}

data class DefaultEntityEvent(
    override val id: String,
    override val type: EntityType,
    override val entityId: String,
    override val traceId: String,
    override val timestamp: Instant,
    override val actions: List<Action>
) : EntityEvent<Action>()