package ru.kontur.docflows.api.repository

import ru.kontur.docflows.api.model.DocflowState

data class UpdateDocflowData(
    val state: DocflowState
)