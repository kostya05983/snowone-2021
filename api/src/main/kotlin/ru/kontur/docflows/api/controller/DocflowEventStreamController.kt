package ru.kontur.docflows.api.controller

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import ru.kontur.docflows.api.dto.events.DocflowEventDto
import ru.kontur.docflows.api.service.DocflowEventStreamService
import ru.kontur.kinfra.events.EventStreamRequest

@Controller
class DocflowEventStreamController(
    private val docflowEventStreamService: DocflowEventStreamService
) {
    @MessageMapping("docflowEventStream")
    fun requestDocflowEventStream(request: EventStreamRequest): Flux<DocflowEventDto> {
        return docflowEventStreamService.events(request.resumeEventId)
    }
}