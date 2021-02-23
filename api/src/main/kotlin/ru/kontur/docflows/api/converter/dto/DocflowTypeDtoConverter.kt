package ru.kontur.docflows.api.converter.dto

import org.springframework.core.convert.converter.Converter
import ru.kontur.docflows.api.dto.docflows.DocflowTypeDto
import ru.kontur.docflows.api.model.DocflowType

object DocflowTypeDtoConverter: Converter<DocflowType, DocflowTypeDto> {
    override fun convert(source: DocflowType): DocflowTypeDto {
        return when(source) {
            DocflowType.HARD_WORK -> DocflowTypeDto.HARD_WORK
            DocflowType.EASY_WORK -> DocflowTypeDto.EASY_WORK
        }
    }
}