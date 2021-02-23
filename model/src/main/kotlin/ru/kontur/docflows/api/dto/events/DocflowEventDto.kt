package ru.kontur.docflows.api.dto.events

import ru.kontur.docflows.api.dto.docflows.DocflowTypeDto
import ru.kontur.kinfra.events.Event
import java.time.Instant

data class DocflowEventDto(
    override val id: String,
    val traceId: String,
    val entityId: String,
    val timestamp: Instant,
    val correlationId: String,
    val actions: List<DocflowActionDto>
) : Event

sealed class DocflowActionDto(
    val type: DocflowActionTypeDto
)

data class SendNotificationActionDto(
    val email: String,
    val docflowType: DocflowTypeDto
) : DocflowActionDto(
    type = DocflowActionTypeDto.SEND_NOTIFICATION
)

data class CreateOnTransportActionDto(
    val name: String,
    val docflowType: DocflowTypeDto
) : DocflowActionDto(DocflowActionTypeDto.TRANSPORT_CREATE)


enum class DocflowActionTypeDto {
    TRANSPORT_CREATE,
    SEND_NOTIFICATION
}