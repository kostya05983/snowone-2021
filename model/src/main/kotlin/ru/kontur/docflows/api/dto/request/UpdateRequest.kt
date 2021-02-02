package ru.kontur.docflows.api.dto.request

import ru.kontur.docflows.api.dto.docflows.DocflowStateDto

data class UpdateRequest(
    val state: DocflowStateDto
)