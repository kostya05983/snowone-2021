package ru.kontur.docflows.api.repository

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import ru.kontur.docflows.api.model.Docflow
import ru.kontur.docflows.api.model.docflows.events.CreateOnTransportAction
import ru.kontur.docflows.api.model.docflows.events.DocflowDomainEvent
import ru.kontur.docflows.api.model.docflows.events.SendNotificationAction
import java.time.Instant
import java.util.*

@Component
@Primary
class DocflowsRepositoryEnricher(
    private val delegate: DocflowsRepositoryImpl
) : DocflowsRepository by delegate {

    override suspend fun save(docflow: Docflow): Docflow {
        return delegate.save(
            docflow.copy(
                event = DocflowDomainEvent(
                    traceId = UUID.randomUUID().toString(),
                    timestamp = Instant.now(),
                    actions = listOf(
                        SendNotificationAction(
                            email = docflow.email,
                            type = docflow.type
                        ),
                        CreateOnTransportAction(
                            name = docflow.name,
                            type = docflow.type
                        )
                    ),
                    correlationId = UUID.randomUUID().toString()
                )
            )
        )
    }
}