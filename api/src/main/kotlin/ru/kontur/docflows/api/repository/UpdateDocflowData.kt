package ru.kontur.docflows.api.repository

import ru.kontur.users.api.model.DocflowState

data class UpdateDocflowData(
    val state: DocflowState
)