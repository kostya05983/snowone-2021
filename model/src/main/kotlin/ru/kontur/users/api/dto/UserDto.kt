package ru.kontur.users.api.dto

data class UserDto(
    val id: String,
    val email: String,
    val name: String,
    val surname: String,
    val patronymic: String?,
    val role: UserRoleDto
)