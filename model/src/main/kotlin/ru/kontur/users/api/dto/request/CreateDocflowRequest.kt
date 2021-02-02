package ru.kontur.users.api.dto.request

import ru.kontur.users.api.dto.users.UserRoleDto

data class CreateDocflowRequest(
    val email: String,
    val name: String,
    val surname: String,
    val patronymic: String?,
    val role: UserRoleDto
)