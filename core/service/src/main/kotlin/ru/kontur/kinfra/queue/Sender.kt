package ru.kontur.kinfra.queue

import ru.kontur.kinfra.events.Event
import java.time.Duration

interface Sender<T : Event> : AutoCloseable {
    suspend fun send(task: T, initialDelay: Duration = Duration.ZERO)
}
