package ru.kontur.users.api.service

import org.springframework.stereotype.Component
import ru.kontur.users.api.dto.CreateUserRequest
import ru.kontur.users.api.repository.UsersRepositoryImpl

@Component
class UsersService(
    private val usersRepository: UsersRepositoryImpl
) {

    fun create(request: CreateUserRequest) {

    }
}