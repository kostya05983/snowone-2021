package ru.kontur.docflows.api.dto.events

import ru.kontur.kinfra.events.Event
import java.time.Instant

data class DocflowEventDto(
    override val id: String,
    val entityId: String,
    val timestamp: Instant,
    val actions: List<UserActionDto>
) : Event

sealed class UserActionDto(
    val type: UserActionTypeDto
)

data class SendNotificationAction(
    val email: String
) : UserActionDto(
    type = UserActionTypeDto.SEND_NOTIFICATION
)


enum class UserActionTypeDto {
    GIVE_ACCESS,
    SEND_NOTIFICATION
}