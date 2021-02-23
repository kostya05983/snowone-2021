package ru.kontur.docflows.api.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.kontur.kinfra.queue.RabbitSyncAsyncQueueFactory
import ru.kontur.kinfra.queue.impl.RabbitConnectionOptions
import ru.kontur.kinfra.queue.impl.RabbitSyncAsyncQueueFactoryImpl

@Configuration
class DocflowsConfiguration(
    private val connectionOptions: RabbitConnectionOptions
) {

    @Bean
    fun rabbitSyncAsyncQueueFactory(): RabbitSyncAsyncQueueFactory {
        return RabbitSyncAsyncQueueFactoryImpl(
            connectionOptions
        )
    }
}