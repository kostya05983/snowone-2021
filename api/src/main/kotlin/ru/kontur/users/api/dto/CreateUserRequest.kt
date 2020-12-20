package ru.kontur.users.api.dto

data class CreateUserRequest(
    val name: String,
    val surname: String,
    val patronymic: String?,
    val email: String
)