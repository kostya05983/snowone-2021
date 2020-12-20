package ru.kontur.users.api.repository

import ru.kontur.users.api.model.User

interface UsersRepository {
    suspend fun save(user: User): User

    suspend fun get(id: String): User?
}