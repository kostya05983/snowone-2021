package ru.kontur.users.api.dto.events

import ru.kontur.kinfra.events.Event
import ru.kontur.users.api.dto.users.UserRoleDto
import java.time.Instant

data class UserEventDto(
    override val id: String,
    val entityId: String,
    val timestamp: Instant,
    val actions: List<UserActionDto>
) : Event

sealed class UserActionDto(
    val type: UserActionTypeDto
)

data class GivAccessAction(
    val role: UserRoleDto
) : UserActionDto(
    type = UserActionTypeDto.GIVE_ACCESS
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