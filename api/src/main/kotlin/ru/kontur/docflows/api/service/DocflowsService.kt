package ru.kontur.docflows.api.service

import org.springframework.stereotype.Component
import ru.kontur.docflows.api.converter.dto.DocflowDtoConverter
import ru.kontur.docflows.api.converter.model.DocflowTypeConverter
import ru.kontur.docflows.api.dto.request.CreateDocflowRequest
import ru.kontur.docflows.api.dto.request.UpdateRequest
import ru.kontur.docflows.api.dto.docflows.DocflowDto
import ru.kontur.docflows.api.exceptions.DocflowNotFoundException
import ru.kontur.docflows.api.exceptions.DocflowUpdateException
import ru.kontur.docflows.api.model.Docflow
import ru.kontur.docflows.api.model.DocflowState
import ru.kontur.docflows.api.repository.DocflowsRepositoryRabbitSubscriber
import ru.kontur.docflows.api.repository.UpdateDocflowData

@Component
class DocflowsService(
    private val docflowsRepository: DocflowsRepositoryRabbitSubscriber
) {
    suspend fun create(request: CreateDocflowRequest): DocflowDto {
        val user = Docflow(
            name = request.name,
            meta = request.meta,
            email = request.email,
            type = DocflowTypeConverter.convert(request.type)
        )
        return docflowsRepository.save(user).let {
            DocflowDtoConverter.convert(it)
        }
    }

    suspend fun update(id: String, request: UpdateRequest) {
        docflowsRepository.get(id) ?: throw DocflowNotFoundException(id)

        val docflowUpdateData = UpdateDocflowData(
            state = DocflowState.valueOf(request.state.name)
        )
        docflowsRepository.update(id, docflowUpdateData) ?: throw DocflowUpdateException(id)
    }

    suspend fun get(id: String): DocflowDto {
        return docflowsRepository.get(id)?.let {
            DocflowDtoConverter.convert(it)
        } ?: throw DocflowNotFoundException(id)
    }
}