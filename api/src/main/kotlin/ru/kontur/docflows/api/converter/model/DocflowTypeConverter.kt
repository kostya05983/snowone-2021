package ru.kontur.docflows.api.converter.model

import org.springframework.core.convert.converter.Converter
import ru.kontur.docflows.api.dto.docflows.DocflowTypeDto
import ru.kontur.docflows.api.model.DocflowType

object DocflowTypeConverter : Converter<DocflowTypeDto, DocflowType> {
    override fun convert(source: DocflowTypeDto): DocflowType {
        return when (source) {
            DocflowTypeDto.EASY_WORK -> DocflowType.EASY_WORK
            DocflowTypeDto.HARD_WORK -> DocflowType.HARD_WORK
        }
    }
}