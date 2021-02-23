package ru.kontur.docflows.api.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.kontur.docflows.api.dto.request.UpdateRequest
import ru.kontur.docflows.api.service.DocflowsService

@RestController
@RequestMapping(
    value = ["/snow-one/docflows/sync"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class DocflowSystemController(
    private val docflowsService: DocflowsService
) {
    @PatchMapping("/{id}")
    suspend fun updateState(@PathVariable id: String, @RequestBody request: UpdateRequest) {
        docflowsService.update(id, request)
    }
}