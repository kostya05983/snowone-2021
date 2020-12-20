package ru.kontur.users.api.converter.dto

import org.springframework.core.convert.converter.Converter
import ru.kontur.users.api.dto.UserStateDto
import ru.kontur.users.api.model.UserState

object UserStateDtoConverter : Converter<UserState, UserStateDto> {
    override fun convert(source: UserState): UserStateDto {
        return when (source) {
            UserState.CREATING -> UserStateDto.CREATING
            UserState.CREATED -> UserStateDto.CREATED
        }
    }
}