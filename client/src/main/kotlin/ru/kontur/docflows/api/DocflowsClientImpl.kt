package ru.kontur.docflows.api

import io.ktor.client.*
import io.ktor.client.request.*
import ru.kontur.docflows.api.dto.request.UpdateRequest
import java.net.URI

class DocflowsClientImpl(
    private val serverHost: String
) : DocflowsApiClient {
    private val client = HttpClient()

    override suspend fun update(id: String, request: UpdateRequest) {
        client.patch<Unit> {
            url(
                URI.create(
                    "$serverHost/snow-one/docflows/sync/$id"
                ).toURL()
            )
            body = request
        }
    }
}