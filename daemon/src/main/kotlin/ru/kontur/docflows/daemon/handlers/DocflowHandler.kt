package ru.kontur.docflows.daemon.handlers

import org.springframework.stereotype.Component
import ru.kontur.docflows.api.dto.events.CreateOnTransportAction
import ru.kontur.docflows.api.dto.events.DocflowEventDto
import ru.kontur.docflows.api.dto.events.SendNotificationAction
import ru.kontur.kinfra.daemons.EventHandler

@Component
class DocflowHandler : EventHandler<DocflowEventDto> {
    override suspend fun handle(item: DocflowEventDto) {
        item.actions.forEach {
            when (it) {
                is SendNotificationAction -> sendNotification()
                is CreateOnTransportAction -> createOnTransport()
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