package ru.kontur.kinfra.queue

interface RabbitSyncAsyncQueueFactory {
    fun <T : Task> receiever(queueName: String, serializer: TaskSerializer<T>): SyncReceiver<T>
    fun <T : Task> sender(queueName: String, serializer: TaskSerializer<T>): Sender<T>
}