package ru.kontur.docflows.api.converter.dto

import org.springframework.core.convert.converter.Converter
import ru.kontur.docflows.api.dto.events.CreateOnTransportActionDto
import ru.kontur.docflows.api.dto.events.DocflowEventDto
import ru.kontur.docflows.api.dto.events.SendNotificationActionDto
import ru.kontur.docflows.api.model.docflows.events.*

object DocflowEventDtoConverter : Converter<DocflowEntityEvent, DocflowEventDto> {
    override fun convert(source: DocflowEntityEvent): DocflowEventDto {
        return DocflowEventDto(
            id = source.id,
            entityId = source.entityId,
            timestamp = source.timestamp,
            actions = source.actions.map {
                when (it) {
                    is CreateOnTransportAction -> {
                        CreateOnTransportActionDto(
                            it.name
                        )
                    }
                    is SendNotificationAction -> {
                        SendNotificationActionDto(
                            it.email
                        )
                    }
                }
            }
        )
    }
}