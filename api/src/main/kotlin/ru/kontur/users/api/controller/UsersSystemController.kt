package ru.kontur.users.api.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.kontur.users.api.dto.request.UpdateRequest

@RestController
@RequestMapping(
    value = ["/snow-one/users/sync"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class UsersSystemController {
    @PatchMapping("/{id}")
    suspend fun updateState(@PathVariable id: String, request: UpdateRequest) {

    }
}