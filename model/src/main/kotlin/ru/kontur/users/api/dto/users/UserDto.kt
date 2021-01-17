package ru.kontur.users.api.dto.users

data class UserDto(
    val id: String,
    val state: UserStateDto,
    val email: String,
    val name: String,
    val surname: String,
    val patronymic: String?,
    val role: UserRoleDto
)