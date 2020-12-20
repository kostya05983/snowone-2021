package ru.kontur.users.api.converter.dto

import org.springframework.core.convert.converter.Converter
import ru.kontur.users.api.dto.users.UserDto
import ru.kontur.users.api.model.User

object UserDtoConverter : Converter<User, UserDto> {
    override fun convert(source: User): UserDto? {
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