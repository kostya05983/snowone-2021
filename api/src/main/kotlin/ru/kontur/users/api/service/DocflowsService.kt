package ru.kontur.users.api.service

import org.springframework.stereotype.Component
import ru.kontur.users.api.converter.dto.UserDtoConverter
import ru.kontur.users.api.dto.request.CreateDocflowRequest
import ru.kontur.users.api.dto.request.UpdateRequest
import ru.kontur.users.api.dto.users.UserDto
import ru.kontur.users.api.exceptions.DocflowNotFoundException
import ru.kontur.users.api.exceptions.DocflowUpdateException
import ru.kontur.users.api.model.Docflow
import ru.kontur.users.api.model.DocflowState
import ru.kontur.users.api.repository.DocflowsRepository
import ru.kontur.users.api.repository.UpdateDocflowData

@Component
class DocflowsService(
    private val docflowsRepository: DocflowsRepository
) {
    suspend fun create(request: CreateDocflowRequest) {
        val user = Docflow(
            name = request.name,
            meta = ""
        )
        return docflowsRepository.save(user).let {
            UserDtoConverter.convert(it)
        }
    }

    suspend fun update(id: String, request: UpdateRequest) {
        docflowsRepository.get(id) ?: throw DocflowNotFoundException(id)

        val docflowUpdateData = UpdateDocflowData(
            state = DocflowState.valueOf(request.state.name)
        )
        docflowsRepository.update(id, docflowUpdateData) ?: throw DocflowUpdateException(id)
    }

    suspend fun get(id: String): UserDto {
        return docflowsRepository.get(id)?.let {
            UserDtoConverter.convert(it)
        } ?: throw DocflowNotFoundException(id)
    }
}