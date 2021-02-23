package ru.kontur.kinfra.rabbit

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

internal const val KONTUR_RABBIT = "kontur.rabbit"

@ConfigurationProperties(KONTUR_RABBIT)
@ConstructorBinding
data class RabbitProperties(
    val enabled: Boolean = false,
    val host: List<String>,
    val username: String = "guest",
    val password: String = "guest",
    val virtualHost: String = "/",
    val environment: String = ""
)
