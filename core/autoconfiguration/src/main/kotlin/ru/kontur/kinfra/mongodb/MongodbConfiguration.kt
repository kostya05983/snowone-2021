package ru.kontur.kinfra.mongodb

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.env.PropertySource
import java.util.*

@Order(Ordered.LOWEST_PRECEDENCE)
class MongodbConfiguration : EnvironmentPostProcessor {

    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {
        if (environment.getProperty(MONGO_ENABLED_PROPERTY)?.toBoolean() == true) {
            val mongoLogin = environment.getProperty(MONGO_LOGIN_PROPERTY)?.takeIf { it.isNotBlank() }
            val mongoPassword = environment.getProperty(MONGO_PASSWORD_PROPERTY)?.takeIf { it.isNotBlank() }
            val mongoDatabase = environment.getProperty(MONGO_DATABASE_PROPERTY)?.takeIf { it.isNotBlank() }
            val mongoReplicaSet = environment.getProperty(MONGO_REPLICA_SET_PROPERTY)?.takeIf { it.isNotBlank() }

            requireNotNull(mongoLogin) { "Missing property '$MONGO_LOGIN_PROPERTY'" }
            requireNotNull(mongoPassword) { "Missing property '$MONGO_PASSWORD_PROPERTY'" }
            requireNotNull(mongoDatabase) { "Missing property '$MONGO_DATABASE_PROPERTY'" }
            requireNotNull(mongoReplicaSet) { "Missing property '$MONGO_REPLICA_SET_PROPERTY'" }

            val defaultProperties = Properties()
            LoggerFactory.getLogger(javaClass).error("mongodb://$mongoLogin:$mongoPassword@$mongoReplicaSet")

            defaultProperties.setProperty("spring.data.mongodb.database", mongoDatabase)
            defaultProperties.setProperty(
                "spring.data.mongodb.uri",
                "mongodb://$mongoLogin:$mongoPassword@$mongoReplicaSet"
            )

            val propertySource: PropertySource<Map<String, Any>> =
                PropertiesPropertySource("kontur.kinfra.mongodb", defaultProperties)

            environment.propertySources.addLast(propertySource)
        }
    }

    companion object {
        const val MONGO_ENABLED_PROPERTY = "$KONTUR_KINFRA_MONGODB.enabled"
        const val MONGO_LOGIN_PROPERTY = "$KONTUR_KINFRA_MONGODB.login"
        const val MONGO_PASSWORD_PROPERTY = "$KONTUR_KINFRA_MONGODB.password"
        const val MONGO_REPLICA_SET_PROPERTY = "$KONTUR_KINFRA_MONGODB.replica-set"
        const val MONGO_DATABASE_PROPERTY = "$KONTUR_KINFRA_MONGODB.database"
    }
}
