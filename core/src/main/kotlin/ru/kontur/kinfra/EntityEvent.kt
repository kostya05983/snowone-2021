package ru.kontur.kinfra

import java.time.Instant
import java.util.*

abstract class EntityEvent<ACTION : Action> : TypedEntityEvent {
    abstract val entityId: String
    abstract val timestamp: Instant
    abstract val traceId: UUID
    abstract val actions: List<ACTION>
}