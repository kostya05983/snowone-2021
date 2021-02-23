package ru.kontur.docflows.api.converter.dto

import org.springframework.core.convert.converter.Converter
import ru.kontur.docflows.api.dto.docflows.DocflowDto
import ru.kontur.docflows.api.model.Docflow

object DocflowDtoConverter : Converter<Docflow, DocflowDto> {
    override fun convert(source: Docflow): DocflowDto? {
        return DocflowDto(
            id = source.id,
            state = DocflowStateDtoConverter.convert(source.state),
            name = source.name,
            meta = source.meta
        )
    }
}