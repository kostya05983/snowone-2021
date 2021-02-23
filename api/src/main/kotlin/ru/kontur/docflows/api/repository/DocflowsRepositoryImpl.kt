package ru.kontur.docflows.api.repository

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Component
import ru.kontur.docflows.api.model.Docflow
import ru.kontur.docflows.api.model.docflows.events.DocflowAction
import ru.kontur.docflows.api.model.docflows.events.DocflowDomainEvent
import ru.kontur.docflows.api.model.docflows.events.DocflowEntityEvent
import ru.kontur.kinfra.events.EntityType
import ru.kontur.kinfra.events.MongoEntityEventSource
import ru.kontur.kinfra.events.MongoEvent

@Component
class DocflowsRepositoryImpl(
    private val template: ReactiveMongoTemplate
) : DocflowsRepository, MongoEntityEventSource<DocflowEntityEvent, DocflowAction, DocflowDomainEvent>(
    template,
    Docflow::class,
    DocflowDomainEvent::class,
    entityType = EntityType.DOCFLOWS
) {
    override fun eventMapper(mongoEvent: MongoEvent): DocflowEntityEvent? {
        val dataBaseEvent = entityEventReader.read(mongoEvent) ?: return null
        return DocflowEntityEvent(
            id = mongoEvent.id,
            type = entityType,
            correlationId = dataBaseEvent.correlationId,
            entityId = requireNotNull(mongoEvent.documentId) { "DocumentId can't be null" },
            timestamp = dataBaseEvent.timestamp,
            traceId = dataBaseEvent.traceId,
            actions = dataBaseEvent.actions
        )
    }

    override suspend fun save(docflow: Docflow): Docflow {
        return template.save(docflow).awaitFirst()
    }

    override suspend fun update(id: String, data: UpdateDocflowData): Docflow? {
        val criteria = Criteria.where(Docflow::id.name).isEqualTo(id)
        val query = Query().addCriteria(criteria)

        val update = Update().apply {
            set(Docflow::state.name, data.state)
        }

        val options = FindAndModifyOptions().returnNew(true)

        return template.findAndModify(query, update, options, Docflow::class.java).awaitFirstOrNull()
    }

    override suspend fun get(id: String): Docflow? {
        val criteria = Criteria.where(Docflow::id.name).isEqualTo(id)
        val query = Query().addCriteria(criteria)

        return template.find(query, Docflow::class.java).awaitFirstOrNull()
    }
}