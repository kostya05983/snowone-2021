package ru.kontur.kinfra.events

import java.time.Instant

abstract class DatabaseEvent<ACTION> {
    abstract var traceId: String
    abstract var timestamp: Instant
    abstract var actions: List<ACTION>
}