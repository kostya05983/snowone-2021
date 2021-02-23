package ru.kontur.docflows.api.converter.dto

import org.springframework.core.convert.converter.Converter
import ru.kontur.docflows.api.dto.docflows.DocflowStateDto
import ru.kontur.docflows.api.model.DocflowState

object DocflowStateDtoConverter : Converter<DocflowState, DocflowStateDto> {
    override fun convert(source: DocflowState): DocflowStateDto {
        return when (source) {
            DocflowState.CREATING -> DocflowStateDto.CREATING
            DocflowState.CREATED -> DocflowStateDto.CREATED
        }
    }
}