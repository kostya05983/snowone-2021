package ru.kontur.docflows.daemon.handlers

import kotlinx.coroutines.delay
import org.springframework.stereotype.Component
import ru.kontur.docflows.api.dto.docflows.DocflowTypeDto
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
                is SendNotificationActionDto -> makeWork(it.docflowType)
                is CreateOnTransportActionDto -> makeWork(it.docflowType)
            }

            val sender = queueFactory.sender(QUEUE_NAME, JsonSerializer(DocflowCreatedResponseRto::class.java))
            val task = DocflowCreatedResponseRto(
                correlationId = item.correlationId,
                traceId = item.traceId
            )
            sender.send(task)
        }
    }

    private suspend fun makeWork(type: DocflowTypeDto) {
        when (type) {
            DocflowTypeDto.EASY_WORK -> {
                delay((100 + 100 * Random().nextInt(10)).toLong())
            }
            DocflowTypeDto.HARD_WORK -> {
                delay((5000 + 1000 * Random().nextInt(10)).toLong())
            }
        }
    }

    private companion object {
        const val QUEUE_NAME = "docflows.save.response"
    }
}