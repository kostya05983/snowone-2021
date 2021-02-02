package ru.kontur.docflows.api.repository

import ru.kontur.docflows.api.model.Docflow

interface DocflowsRepository {
    suspend fun save(docflow: Docflow): Docflow

    suspend fun update(id: String, data: UpdateDocflowData): Docflow?

    suspend fun get(id: String): Docflow?
}