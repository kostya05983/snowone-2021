package ru.kontur.kinfra.queue.impl

import ru.kontur.kinfra.queue.*

class RabbitSyncAsyncQueueFactoryImpl(
    private val connectionOptions: RabbitConnectionOptions
) : RabbitSyncAsyncQueueFactory {
    override fun <T : Task> receiever(queueName: String, serializer: TaskSerializer<T>): SyncReceiver<T> {
        val queue = RabbitSyncAsyncTaskQueue(connectionOptions, queueName, serializer)
        return queue.receiver()
    }

    override fun <T : Task> sender(queueName: String, serializer: TaskSerializer<T>): Sender<T> {
        val queue = RabbitSyncAsyncTaskQueue(connectionOptions, queueName, serializer)
        return queue.sender()
    }
}
