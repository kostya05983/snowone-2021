package ru.kontur.kinfra.queue.impl

data class RabbitConnectionOptions(
    val host: List<String>,
    val username: String = "guest",
    val password: String = "guest",
    val virtualHost: String = "/",
    val environment: String = ""
)
