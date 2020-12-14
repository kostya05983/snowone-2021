package ru.kontur.kinfra

import com.mongodb.client.model.changestream.OperationType
import com.mongodb.client.model.changestream.UpdateDescription
import org.bson.Document

data class MongoEvent(
    override val id: String,
    val operationType: OperationType,
    val database: String?,
    val collection: String?,
    val documentId: String?,
    val fullDocument: Document?,
    val updateDescription: UpdateDescription?
): Event {
    override fun toString(): String {
        return "MongoEvent(id=${id}, documentId=${documentId})"
    }
}