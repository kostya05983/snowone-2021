package ru.kontur.users.api.converter.model

import org.springframework.core.convert.converter.Converter
import ru.kontur.users.api.dto.UserRoleDto
import ru.kontur.users.api.model.UserRole

object UserConverter : Converter<UserRoleDto, UserRole> {
    override fun convert(source: UserRoleDto): UserRole {
        return when (source) {
            UserRoleDto.TERMINATOR -> UserRole.TERMINATOR
            UserRoleDto.MANAGER -> UserRole.MANAGER
            UserRoleDto.EMPLOYEE -> UserRole.EMPLOYEE
        }
    }
}