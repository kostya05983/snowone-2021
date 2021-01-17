package ru.kontur.users.api

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import ru.kontur.users.api.dto.request.UpdateRequest

@FeignClient(name = "users-client", url = "http://localhost:8080")
interface UsersApiClient {
    @PatchMapping("/snow-one/users/sync/{id}")
    fun update(@PathVariable id: String, @RequestBody request: UpdateRequest)
}