package ru.kontur.docflows.api.repository

import org.springframework.stereotype.Component
import ru.kontur.docflows.api.model.Docflow
import ru.kontur.docflows.api.model.DocflowState
import ru.kontur.docflows.events.DocflowCreatedResponseRto
import ru.kontur.kinfra.queue.OperationResult
import ru.kontur.kinfra.queue.RabbitSyncAsyncQueueFactory
import ru.kontur.kinfra.queue.impl.JsonSerializer
import java.util.*

@Component
class DocflowsRepositoryRabbitSubscriber(
    private val docflowRepository: DocflowsRepositoryEnricher,
    private val rabbitFactory: RabbitSyncAsyncQueueFactory
) : DocflowsRepository by docflowRepository {

    override suspend fun save(docflow: Docflow): Docflow {
        val saved = docflowRepository.save(docflow)
        val correlationId = saved.event?.correlationId ?: UUID.randomUUID().toString()
        val receiver = rabbitFactory.receiever(QUEUE_NAME, JsonSerializer(DocflowCreatedResponseRto::class.java))
        return when (receiver.await(correlationId = correlationId)) {
            is OperationResult.Success -> {
                saved.copy(state = DocflowState.CREATED)
            }
            is OperationResult.Fail -> {
                saved
            }
        }
    }

    private companion object {
        const val QUEUE_NAME = "docflows.save.response"
    }
}