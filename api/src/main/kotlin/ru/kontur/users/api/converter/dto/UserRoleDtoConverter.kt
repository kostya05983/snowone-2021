package ru.kontur.users.api.converter.dto

import org.springframework.core.convert.converter.Converter
import ru.kontur.users.api.dto.UserRoleDto
import ru.kontur.users.api.model.UserRole

object UserRoleDtoConverter : Converter<UserRole, UserRoleDto> {
    override fun convert(source: UserRole): UserRoleDto {
        return when (source) {
            UserRole.TERMINATOR -> UserRoleDto.TERMINATOR
            UserRole.MANAGER -> UserRoleDto.MANAGER
            UserRole.EMPLOYEE -> UserRoleDto.EMPLOYEE
        }
    }
}