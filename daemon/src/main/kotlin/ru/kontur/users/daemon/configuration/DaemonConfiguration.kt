package ru.kontur.users.daemon.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.kontur.users.api.UsersApiClient
import ru.kontur.users.api.UsersClientImpl

@Configuration
class DaemonConfiguration {

    @Bean
    fun usersClient(): UsersApiClient {
        return UsersClientImpl(
            serverHost = "localhost:8080"
        )
    }
}