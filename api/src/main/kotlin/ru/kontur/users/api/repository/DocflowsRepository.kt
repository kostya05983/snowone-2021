package ru.kontur.users.api.repository

import ru.kontur.users.api.model.Docflow

interface DocflowsRepository {
    suspend fun save(docflow: Docflow): Docflow

    suspend fun update(id: String, data: UpdateDocflowData): Docflow?

    suspend fun get(id: String): Docflow?
}