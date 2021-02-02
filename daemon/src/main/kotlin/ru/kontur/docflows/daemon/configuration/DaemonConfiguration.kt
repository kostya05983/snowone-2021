package ru.kontur.docflows.daemon.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.kontur.docflows.api.DocflowsApiClient
import ru.kontur.docflows.api.DocflowsClientImpl

@Configuration
class DaemonConfiguration {

    @Bean
    fun docflowsClient(): DocflowsApiClient {
        return DocflowsClientImpl(
            serverHost = "localhost:8080"
        )
    }
}