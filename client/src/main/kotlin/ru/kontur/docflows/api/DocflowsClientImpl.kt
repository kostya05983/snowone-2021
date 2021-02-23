package ru.kontur.docflows.api

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.kontur.docflows.api.dto.request.UpdateRequest
import java.net.URI

class DocflowsClientImpl(
    private val serverHost: String
) : DocflowsApiClient {
    private val client = HttpClient() {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }

    override suspend fun update(id: String, request: UpdateRequest) {
        client.patch<Unit> {
            url(
                URI.create(
                    "$serverHost/snow-one/docflows/sync/$id"
                ).toURL()
            )
            headers {
                contentType(ContentType.Application.Json)
            }
            body = request
        }
    }
}