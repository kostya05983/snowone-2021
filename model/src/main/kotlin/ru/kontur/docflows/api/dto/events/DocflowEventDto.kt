package ru.kontur.docflows.api.dto.events

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
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

@JsonTypeInfo(property = "type", use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes(
    JsonSubTypes.Type(SendNotificationActionDto::class, name = "SEND_NOTIFICATION"),
    JsonSubTypes.Type(CreateOnTransportActionDto::class, name = "TRANSPORT_CREATE")
)
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