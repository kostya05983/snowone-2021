package ru.kontur.users.api.service

import org.springframework.stereotype.Component
import ru.kontur.users.api.converter.dto.UserDtoConverter
import ru.kontur.users.api.converter.model.UserConverter
import ru.kontur.users.api.converter.model.UserStateConverter
import ru.kontur.users.api.dto.CreateUserRequest
import ru.kontur.users.api.dto.UserDto
import ru.kontur.users.api.exceptions.UserNotFoundException
import ru.kontur.users.api.model.User
import ru.kontur.users.api.repository.UsersRepository

@Component
class UsersService(
    private val usersRepository: UsersRepository
) {
    suspend fun create(request: CreateUserRequest) {
        val user = User(
            email = request.email,
            name = request.name,
            surname = request.surname,
            patronymic = request.patronymic,
            role = UserConverter.convert(request.role)
        )
        return usersRepository.save(user).let {
            UserDtoConverter.convert(it)
        }
    }

    suspend fun get(id: String): UserDto {
        return usersRepository.get(id)?.let {
            UserDtoConverter.convert(it)
        } ?: throw UserNotFoundException(id)
    }
}