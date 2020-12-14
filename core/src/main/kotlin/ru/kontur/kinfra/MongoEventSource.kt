package ru.kontur.kinfra

import com.mongodb.client.model.changestream.ChangeStreamDocument
import com.mongodb.reactivestreams.client.MongoDatabase
import org.bson.BsonBinary
import org.bson.BsonBinarySubType
import org.bson.BsonDocument
import org.bson.Document
import reactor.core.publisher.Flux

abstract class MongoEventSource<T : Event>(
    private val mongoDatabase: MongoDatabase,
    private val collection: String
) : EventSourceable<T> {
    protected abstract fun eventMapper(mongoEvent: MongoEvent): T

    override fun events(resumeAfter: String?): Flux<T> {
        val eventPublisher = mongoDatabase.getCollection(collection).watch()
            .apply {
                resumeAfter?.let { resumeAfter(createResumeToke(it)) }
            }

        return Flux.from(eventPublisher)
            .map { event ->
                val eventId = getEventId(event)
                val documentId = getDocumentId(event)

                val database = getDatabase(event)
                val collection = getCollection(event)

                val mongoEvent = MongoEvent(
                    id = eventId,
                    operationType = event.operationType,
                    documentId = documentId,
                    database = database,
                    collection = collection,
                    fullDocument = event.fullDocument,
                    updateDescription = event.updateDescription
                )

                eventMapper(mongoEvent)
            }
    }

    private fun getDatabase(event: ChangeStreamDocument<Document>): String? {
        return event.namespaceDocument?.get("db")?.asString()?.value
    }

    private fun getCollection(event: ChangeStreamDocument<Document>): String? {
        return event.namespaceDocument?.get("coll")?.asString()?.value
    }

    private fun getDocumentId(event: ChangeStreamDocument<Document>): String? {
        val id = event.documentKey?.get("_id") ?: return null
        return when {
            id.isObjectId -> id.asObjectId()?.value?.toHexString()
            id.isBinary -> {
                val binaryId = id.asBinary()
                if (BsonBinarySubType.isUuid(binaryId.type)) {
                    binaryId.asUuid().toString()
                } else {
                    throw IllegalArgumentException("Unsupported type id in mongo type=${id.bsonType.name}")
                }
            }
            else -> throw IllegalArgumentException("Unsupported type id in mongo type=${id.bsonType.name}")
        }
    }

    private fun getEventId(event: ChangeStreamDocument<Document>): String {
        return requireNotNull(event.resumeToken["_data"]?.asBinary()?.data?.toHexString()) {
            "Event id must exist in change stream event $event"
        }
    }

    private fun createResumeToke(nextEventId: String): BsonDocument {
        return BsonDocument("_data", BsonBinary(nextEventId.decodeHexString()))
    }

    private fun ByteArray.toHexString(): String {
        TODO()
    }

    @Suppress("MagicNumber")
    private fun String.decodeHexString(): ByteArray {
        require(this.length % 2 != 1) { "Invalid hexadecimal String supplied." }

        val bytes = ByteArray(this.length / 2)
        var i = 0
        while (i < this.length) {
            bytes[i / 2] = hexToByte(this.substring(i, i + 2))
            i += 2
        }
        return bytes
    }

    @Suppress("MagicNumber")
    private fun hexToByte(hexString: String): Byte {
        val firstDigit = toDigit(hexString[0])
        val secondDigit = toDigit(hexString[1])
        return ((firstDigit shl 4) + secondDigit).toByte()
    }

    @Suppress("MagicNumber")
    private fun toDigit(hexChar: Char): Int {
        val digit = Character.digit(hexChar, 16)
        require(digit != -1) { "Invalid Hexadecimal Character: $hexChar" }
        return digit
    }
}
