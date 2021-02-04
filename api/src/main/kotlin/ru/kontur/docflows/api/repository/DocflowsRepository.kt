package ru.kontur.docflows.api.repository

import ru.kontur.docflows.api.model.Docflow
import ru.kontur.docflows.api.model.docflows.events.DocflowEntityEvent
import ru.kontur.kinfra.events.EventSourceable

interface DocflowsRepository: EventSourceable<DocflowEntityEvent> {
    suspend fun save(docflow: Docflow): Docflow

    suspend fun update(id: String, data: UpdateDocflowData): Docflow?

    suspend fun get(id: String): Docflow?
}