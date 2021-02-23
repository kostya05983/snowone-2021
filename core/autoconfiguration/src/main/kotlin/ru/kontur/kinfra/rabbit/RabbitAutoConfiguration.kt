package ru.kontur.kinfra.rabbit

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import ru.kontur.kinfra.queue.impl.RabbitConnectionOptions

@ConditionalOnMissingBean(RabbitConnectionOptions::class)
@EnableConfigurationProperties(RabbitProperties::class)
@ConditionalOnProperty(prefix = KONTUR_RABBIT, name = ["enabled"], havingValue = "true")
class RabbitAutoConfiguration(
    private val properties: RabbitProperties
) {
    @Bean
    fun rabbitConnectionOptions(): RabbitConnectionOptions = RabbitConnectionOptions(
        host = properties.host,
        username = properties.username,
        password = properties.password,
        virtualHost = properties.virtualHost,
        environment = properties.environment
    )
}
