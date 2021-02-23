package ru.kontur.kinfra.mongodb

import org.springframework.boot.context.properties.ConfigurationProperties

internal const val KONTUR_KINFRA_MONGODB = "kontur.kinfra.mongodb"

@ConfigurationProperties(KONTUR_KINFRA_MONGODB)
data class MongodbProperties(
    var enabled: Boolean = false,
    var login: String? = null,
    var password: String? = null,
    var replicaSet: String? = null,
    var database: String? = null
)
