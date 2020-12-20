package ru.kontur.users.api.converter.model

import org.springframework.core.convert.converter.Converter
import ru.kontur.users.api.dto.UserStateDto
import ru.kontur.users.api.model.UserState

object UserStateConverter : Converter<UserStateDto, UserState> {
    override fun convert(source: UserStateDto): UserState? {
        return when (source) {
            UserStateDto.CREATING -> UserState.CREATING
            UserStateDto.CREATED -> UserState.CREATED
        }
    }
}