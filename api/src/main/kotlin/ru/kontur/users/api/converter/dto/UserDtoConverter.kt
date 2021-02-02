package ru.kontur.users.api.converter.dto

import org.springframework.core.convert.converter.Converter
import ru.kontur.users.api.dto.users.UserDto
import ru.kontur.users.api.model.Docflow

object UserDtoConverter : Converter<Docflow, UserDto> {
    override fun convert(source: Docflow): UserDto? {
        return UserDto(
            id = source.id,
            state = UserStateDtoConverter.convert(source.state),
            email = source.email,
            name = source.name,
            surname = source.surname,
            patronymic = source.patronymic,
            role = UserRoleDtoConverter.convert(source.role)
        )
    }
}