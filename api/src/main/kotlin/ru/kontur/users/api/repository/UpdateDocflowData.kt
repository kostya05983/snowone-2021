package ru.kontur.users.api.repository

import ru.kontur.users.api.model.DocflowState

data class UpdateDocflowData(
    val state: DocflowState
)