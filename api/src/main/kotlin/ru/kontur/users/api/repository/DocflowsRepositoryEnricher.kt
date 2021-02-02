package ru.kontur.users.api.repository

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import ru.kontur.users.api.model.Docflow

@Component
@Primary
class DocflowsRepositoryEnricher(
    private val delegate: DocflowsRepository
) : DocflowsRepository by delegate {

    override suspend fun save(docflow: Docflow): Docflow {
        return delegate.save(
            docflow.copy(

            )
        )
    }
}