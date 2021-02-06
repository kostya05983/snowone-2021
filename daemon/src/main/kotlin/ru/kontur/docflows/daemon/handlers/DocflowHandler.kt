package ru.kontur.docflows.daemon.handlers

import org.springframework.stereotype.Component
import ru.kontur.docflows.api.dto.events.CreateOnTransportActionDto
import ru.kontur.docflows.api.dto.events.DocflowEventDto
import ru.kontur.docflows.api.dto.events.SendNotificationActionDto
import ru.kontur.docflows.events.DocflowCreatedResponseRto
import ru.kontur.kinfra.daemons.EventHandler
import ru.kontur.kinfra.queue.RabbitSyncAsyncQueueFactory
import ru.kontur.kinfra.queue.impl.JsonSerializer
import java.util.*

@Component
class DocflowHandler(
    private val queueFactory: RabbitSyncAsyncQueueFactory
) : EventHandler<DocflowEventDto> {
    override suspend fun handle(item: DocflowEventDto) {
        item.actions.forEach {
            when (it) {
                is SendNotificationActionDto -> sendNotification()
                is CreateOnTransportActionDto -> createOnTransport()
            }
        }
    }

    suspend fun sendNotification(traceId: UUID, correlationId: String) {
        TODO("logging")

        val sender = queueFactory.sender(QUEUE_NAME, JsonSerializer(DocflowCreatedResponseRto::class.java))
        val task = DocflowCreatedResponseRto(
            correlationId = correlationId,
            traceId = traceId
        )
        sender.send(task)
    }

    fun createOnTransport() {
        TODO("logging")
    }

    private companion object {
        const val QUEUE_NAME = "docflows.save.response"
    }
}