package ru.kontur.docflows.daemon.handlers

import org.springframework.stereotype.Component
import ru.kontur.docflows.api.dto.events.CreateOnTransportActionDto
import ru.kontur.docflows.api.dto.events.DocflowEventDto
import ru.kontur.docflows.api.dto.events.SendNotificationActionDto
import ru.kontur.kinfra.daemons.EventHandler

@Component
class DocflowHandler : EventHandler<DocflowEventDto> {
    override suspend fun handle(item: DocflowEventDto) {
        item.actions.forEach {
            when (it) {
                is SendNotificationActionDto -> sendNotification()
                is CreateOnTransportActionDto -> createOnTransport()
            }
        }
    }

    fun sendNotification() {
        TODO("logging")
    }

    fun createOnTransport() {
        TODO("logging")
    }
}