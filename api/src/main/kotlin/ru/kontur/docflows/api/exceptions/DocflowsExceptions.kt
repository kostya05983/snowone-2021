package ru.kontur.docflows.api.exceptions

import org.springframework.http.HttpStatus
import java.lang.Exception

sealed class DocflowsExceptions(
    message: String,
    val status: HttpStatus
) : Exception(message)

class DocflowNotFoundException(
    id: String
) : DocflowsExceptions(
    message = "User by id=$id was not found",
    status = HttpStatus.NOT_FOUND
)

class DocflowUpdateException(
    id: String
) : DocflowsExceptions(
    message = "Maybe concurrent modify $id",
    status = HttpStatus.CONFLICT
)