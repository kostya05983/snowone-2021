package ru.kontur.users.api.model

import org.bson.types.ObjectId
import ru.kontur.users.api.model.users.events.UserDomainEvent

data class User(
    val id: String = ObjectId().toHexString(),

    val state: UserState = UserState.CREATING,

    val email: String,

    val name: String,

    val surname: String,

    val patronymic: String?,

    val role: UserRole,

    val event: UserDomainEvent? = null
)