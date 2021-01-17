package ru.kontur.kinfra.events

import com.mongodb.client.model.changestream.OperationType
import org.bson.Document
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import kotlin.reflect.KClass

class CompositeEventReader<EVENT : DatabaseEvent<*>>(
    template: ReactiveMongoTemplate,
    eventField: String = "event",
    kClass: KClass<EVENT>
) : EntityEventReader<EVENT> {
    private val readers: Map<OperationType, EntityEventReader<EVENT>>

    init {
        val codec = template.mongoDatabase.block()!!.codecRegistry[Document::class.java]
        readers = mutableMapOf(
            OperationType.INSERT to InsertEventReader(
                template.converter, eventField, kClass
            ),
            OperationType.UPDATE to UpdateEventReader(
                template.converter, codec, eventField, kClass
            )
        )
    }

    override fun read(mongoEvent: MongoEvent): EVENT? {
        return readers[mongoEvent.operationType]?.read(mongoEvent)
    }
}