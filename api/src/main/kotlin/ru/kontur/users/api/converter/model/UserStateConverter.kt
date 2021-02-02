package ru.kontur.users.api.converter.model

import org.springframework.core.convert.converter.Converter
import ru.kontur.users.api.dto.users.UserStateDto
import ru.kontur.users.api.model.DocflowState

object UserStateConverter : Converter<UserStateDto, DocflowState> {
    override fun convert(source: UserStateDto): DocflowState? {
        return when (source) {
            UserStateDto.CREATING -> DocflowState.CREATING
            UserStateDto.CREATED -> DocflowState.CREATED
        }
    }
}