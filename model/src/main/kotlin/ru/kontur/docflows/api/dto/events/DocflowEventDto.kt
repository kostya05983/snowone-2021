package ru.kontur.docflows.api.dto.events

import ru.kontur.kinfra.events.Event
import java.time.Instant

data class DocflowEventDto(
    override val id: String,
    val entityId: String,
    val timestamp: Instant,
    val actions: List<DocflowActionDto>
) : Event

sealed class DocflowActionDto(
    val type: DocflowActionTypeDto
)

data class SendNotificationAction(
    val email: String
) : DocflowActionDto(
    type = DocflowActionTypeDto.SEND_NOTIFICATION
)

data class CreateOnTransportAction(
    val name: String
) : DocflowActionDto(DocflowActionTypeDto.TRANSPORT_CREATE)


enum class DocflowActionTypeDto {
    TRANSPORT_CREATE,
    SEND_NOTIFICATION
}