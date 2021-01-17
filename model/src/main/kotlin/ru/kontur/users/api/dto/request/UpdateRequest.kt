package ru.kontur.users.api.dto.request

import ru.kontur.users.api.dto.users.UserStateDto

data class UpdateRequest(
    val state: UserStateDto
)