package ru.kontur.users.api.repository

import org.bson.types.ObjectId
import ru.kontur.users.api.model.User

interface UsersRepository {
    suspend fun save(user: User): User

    suspend fun get(id: ObjectId): User?
}