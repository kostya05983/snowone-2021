package ru.kontur.docflows.api.service

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.asFlux
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import ru.kontur.docflows.api.converter.dto.DocflowEventDtoConverter
import ru.kontur.docflows.api.dto.events.DocflowEventDto
import ru.kontur.docflows.api.repository.DocflowsRepository

@Component
class DocflowEventStreamService(
    private val docflowRepository: DocflowsRepository
) {
    fun events(resumeAfter: String?): Flux<DocflowEventDto> {
        return docflowRepository.events(resumeAfter).map {
            DocflowEventDtoConverter.convert(it)
        }.asFlux()
    }
}