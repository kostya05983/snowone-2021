package ru.kontur.kinfra.queue

import ru.kontur.kinfra.events.Event
import java.util.*

abstract class Task : Event {
    override val id: String = UUID.randomUUID().toString()
    open val taskId: UUID = UUID.randomUUID()
    abstract val traceId: UUID
    abstract val correlationId: String
}
