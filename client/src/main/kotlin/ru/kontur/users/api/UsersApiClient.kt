package ru.kontur.users.api

import ru.kontur.users.api.dto.request.UpdateRequest

interface UsersApiClient {
    suspend fun update(id: String, request: UpdateRequest)
}