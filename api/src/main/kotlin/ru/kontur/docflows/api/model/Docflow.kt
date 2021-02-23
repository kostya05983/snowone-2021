package ru.kontur.docflows.api.model

import org.bson.types.ObjectId
import ru.kontur.docflows.api.model.docflows.events.DocflowDomainEvent

data class Docflow(
    val id: String = ObjectId().toHexString(),

    val type: DocflowType,

    val state: DocflowState = DocflowState.CREATING,

    val name: String,

    val email: String,

    val meta: String,

    val event: DocflowDomainEvent? = null
)