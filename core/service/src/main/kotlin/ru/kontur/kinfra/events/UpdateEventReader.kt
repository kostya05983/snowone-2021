package ru.kontur.kinfra.events

import org.bson.Document
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.springframework.data.mongodb.core.convert.MongoConverter
import kotlin.reflect.KClass

class UpdateEventReader<EVENT : DatabaseEvent<*>>(
    private val converter: MongoConverter,
    private val codec: Codec<Document>,
    private val eventField: String,
    private val kClass: KClass<EVENT>
) : EntityEventReader<EVENT> {
    override fun read(mongoEvent: MongoEvent): EVENT? {
        return mongoEvent.updateDescription?.updatedFields?.get(eventField)?.asDocument()?.let {
            val document = codec.decode(it.asBsonReader(), DecoderContext.builder().build())
            converter.read(kClass.java, document)
        } ?: return null
    }
}