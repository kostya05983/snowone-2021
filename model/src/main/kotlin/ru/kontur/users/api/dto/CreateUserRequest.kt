package ru.kontur.users.api.dto

data class CreateUserRequest(
    val email: String,
    val name: String,
    val surname: String,
    val patronymic: String?,
    val role: UserRoleDto
)