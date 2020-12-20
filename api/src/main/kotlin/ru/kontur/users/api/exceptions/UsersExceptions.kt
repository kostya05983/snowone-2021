package ru.kontur.users.api.exceptions

import org.springframework.http.HttpStatus
import java.lang.Exception

sealed class UsersExceptions(
    message: String,
    val status: HttpStatus
) : Exception(message)

class UserNotFoundException(
    id: String
) : UsersExceptions(
    message = "User by id=$id was not found",
    status = HttpStatus.NOT_FOUND
)