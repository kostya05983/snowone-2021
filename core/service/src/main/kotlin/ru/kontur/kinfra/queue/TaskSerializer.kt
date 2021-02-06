package ru.kontur.kinfra.queue

import ru.kontur.kinfra.events.Event

interface TaskSerializer<T : Event> {
    val contentType: String

    fun serialize(task: T): ByteArray

    fun deserialize(task: ByteArray): T
}
