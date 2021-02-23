package ru.kontur.docflows.api.dto.request

import ru.kontur.docflows.api.dto.docflows.DocflowTypeDto

data class CreateDocflowRequest(
    val email: String,
    val name: String,
    val type: DocflowTypeDto
)