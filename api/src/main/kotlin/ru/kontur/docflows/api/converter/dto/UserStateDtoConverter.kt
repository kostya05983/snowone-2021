package ru.kontur.docflows.api.converter.dto

import org.springframework.core.convert.converter.Converter
import ru.kontur.users.api.dto.users.UserStateDto
import ru.kontur.users.api.model.DocflowState

object UserStateDtoConverter : Converter<DocflowState, UserStateDto> {
    override fun convert(source: DocflowState): UserStateDto {
        return when (source) {
            DocflowState.CREATING -> UserStateDto.CREATING
            DocflowState.CREATED -> UserStateDto.CREATED
        }
    }
}