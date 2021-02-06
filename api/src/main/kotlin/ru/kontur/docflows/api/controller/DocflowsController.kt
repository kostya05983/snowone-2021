package ru.kontur.docflows.api.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import ru.kontur.docflows.api.dto.events.DocflowEventDto
import ru.kontur.docflows.api.dto.request.CreateDocflowRequest
import ru.kontur.docflows.api.service.DocflowEventStreamService
import ru.kontur.docflows.api.service.DocflowsService

@RestController
@RequestMapping(
    value = ["/snow-one/docflows"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class DocflowsController(
    private val docflowsService: DocflowsService,
    private val docflowEventStreamService: DocflowEventStreamService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createDocflow(
        @RequestBody request: CreateDocflowRequest
    ) {
        docflowsService.create(request)
    }

    @GetMapping(value = ["/{id}"])
    suspend fun getDocflow(
        @PathVariable id: String
    ) {
        docflowsService.get(id)
    }

    @GetMapping(value = ["/subscribe"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun subscribe(@RequestParam id: String?): Flux<DocflowEventDto> {
        return docflowEventStreamService.events(id)
    }
}