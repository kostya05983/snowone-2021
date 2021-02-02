package ru.kontur.docflows.api

import ru.kontur.docflows.api.dto.request.UpdateRequest

interface DocflowsApiClient {
    suspend fun update(id: String, request: UpdateRequest)
}