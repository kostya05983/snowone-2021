package ru.kontur.users.api.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.kontur.users.api.dto.request.CreateDocflowRequest
import ru.kontur.users.api.service.DocflowsService

@RestController
@RequestMapping(
    value = ["/snow-one/docflows"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class DocflowsController(
    private val docflowsService: DocflowsService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createUser(
        @RequestBody request: CreateDocflowRequest
    ) {
        docflowsService.create(request)
    }

    @GetMapping(value = ["/{id}"])
    suspend fun getUser(
        @PathVariable id: String
    ) {
        docflowsService.get(id)
    }
}