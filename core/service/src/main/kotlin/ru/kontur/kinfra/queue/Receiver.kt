package ru.kontur.kinfra.queue

import ru.kontur.kinfra.events.Event

interface Receiver<T : Event> : AutoCloseable

interface SyncReceiver<T : Task> : Receiver<T> {
    suspend fun await(correlationId: String): OperationResult<T, Throwable>
}
