package ru.kontur.users.api.model

data class User(
    val id: String,
    val email: String,
    val name: String,
    val surname: String,
    val patronymic: String?,
    val role: UserRole
)