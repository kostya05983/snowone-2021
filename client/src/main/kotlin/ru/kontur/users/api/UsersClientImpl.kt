package ru.kontur.users.api

import io.ktor.client.*
import io.ktor.client.request.*
import ru.kontur.users.api.dto.request.UpdateRequest
import java.net.URI

class UsersClientImpl(
    private val serverHost: String
) : UsersApiClient {
    private val client = HttpClient()

    override suspend fun update(id: String, request: UpdateRequest) {
        client.patch<Unit> {
            url(
                URI.create(
                    "$serverHost/snow-one/users/sync/$id"
                ).toURL()
            )
            body = request
        }
    }
}