package ru.kontur.kinfra.events

import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import kotlin.reflect.KClass

abstract class MongoEntityEventSource<EVENT : EntityEvent<ACTION>, ACTION : Action, DATA : DatabaseEvent<ACTION>>(
    mongoTemplate: ReactiveMongoTemplate,
    clazz: KClass<*>,
    databaseClazz: KClass<DATA>,
    protected val entityEventReader: EntityEventReader<DATA> = CompositeEventReader(
        mongoTemplate,
        "event",
        databaseClazz
    ),
    protected val entityType: EntityType
) : MongoEventSource<EVENT>(
    requireNotNull(mongoTemplate.mongoDatabase.block()) { "Something went wrong database can't be null" },
    mongoTemplate.getCollectionName(clazz.java)
) {
    override fun eventMapper(mongoEvent: MongoEvent): EVENT? {
        val dataBaseEvent = entityEventReader.read(mongoEvent) ?: return null
        return DefaultEntityEvent(
            id = mongoEvent.id,
            type = entityType,
            entityId = requireNotNull(mongoEvent.documentId) { "DocumentId can't be null" },
            timestamp = dataBaseEvent.timestamp,
            traceId = dataBaseEvent.traceId,
            actions = dataBaseEvent.actions
        ) as? EVENT
    }
}