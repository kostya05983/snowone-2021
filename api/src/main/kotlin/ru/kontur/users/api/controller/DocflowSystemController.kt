package ru.kontur.users.api.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.kontur.users.api.dto.request.UpdateRequest
import ru.kontur.users.api.service.DocflowsService

@RestController
@RequestMapping(
    value = ["/snow-one/docflows/sync"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class DocflowSystemController(
    private val docflowsService: DocflowsService
) {
    @PatchMapping("/{id}")
    suspend fun updateState(@PathVariable id: String, request: UpdateRequest) {
        docflowsService.update(id, request)
    }
}