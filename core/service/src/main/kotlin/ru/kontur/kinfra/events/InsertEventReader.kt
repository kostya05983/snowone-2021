package ru.kontur.kinfra.events

import org.bson.Document
import org.springframework.data.mongodb.core.convert.MongoConverter
import kotlin.reflect.KClass

class InsertEventReader<EVENT : DatabaseEvent<*>>(
    private val converter: MongoConverter,
    private val eventField: String,
    private val kClass: KClass<EVENT>
) : EntityEventReader<EVENT> {
    override fun read(mongoEvent: MongoEvent): EVENT? {
        return (mongoEvent.fullDocument?.get(eventField) as? Document)?.let {
            converter.read(kClass.java, it)
        } ?: return null
    }
}