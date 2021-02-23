package ru.kontur.docflows.api.dto.docflows

data class DocflowDto(
    val id: String,
    val type: DocflowTypeDto,
    val state: DocflowStateDto,
    val name: String,
    val meta: String
)