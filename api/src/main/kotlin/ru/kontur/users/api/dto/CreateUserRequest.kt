package ru.kontur.users.api.dto

import ru.kontur.users.api.model.UserRole

data class CreateUserRequest(
    val email: String,
    val name: String,
    val surname: String,
    val patronymic: String?,
    val role: UserRole
)