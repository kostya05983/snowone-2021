package ru.kontur.docflows.api.dto.request

data class CreateDocflowRequest(
    val email: String,
    val name: String,
    val surname: String,
    val patronymic: String?
)