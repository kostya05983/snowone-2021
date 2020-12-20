package ru.kontur.users.api.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.kontur.users.api.dto.CreateUserRequest
import ru.kontur.users.api.service.UsersService

@RestController
@RequestMapping(
    value = ["/snow-one/users"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class UsersController(
    private val usersService: UsersService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createUser(
        @RequestBody request: CreateUserRequest
    ) {
        usersService.create(request)
    }
}