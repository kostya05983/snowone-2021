package ru.kontur.kinfra.events

import java.time.Instant
import java.util.*

abstract class EntityEvent<ACTION : Action> : TypedEntityEvent {
    abstract val entityId: String
    abstract val timestamp: Instant
    abstract val traceId: UUID
    abstract val actions: List<ACTION>
}