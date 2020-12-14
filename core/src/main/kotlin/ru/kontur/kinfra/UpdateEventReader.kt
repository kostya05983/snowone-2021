package ru.kontur.kinfra

import org.bson.Document
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.springframework.data.mongodb.core.convert.MongoConverter
import kotlin.reflect.KClass

class UpdateEventReader<T : EntityEvent>(
    private val converter: MongoConverter,
    private val codec: Codec<Document>,
    private val eventField: String,
    private val kClass: KClass<T>
) {
    fun read(mongoEvent: MongoEvent): T? {
        return mongoEvent.updateDescription?.updatedFields?.get(eventField)?.asDocument()?.let {
            val document = codec.decode(it.asBsonReader(), DecoderContext.builder().build())
            converter.read(kClass.java, document)
        } ?: return null
    }
}