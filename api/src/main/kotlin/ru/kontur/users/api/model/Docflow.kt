package ru.kontur.users.api.model

import org.bson.types.ObjectId
import ru.kontur.users.api.model.users.events.UserDomainEvent

data class Docflow(
    val id: String = ObjectId().toHexString(),

    val state: DocflowState = DocflowState.CREATING,

    val name: String,

    val meta: String,

    val event: UserDomainEvent? = null
)