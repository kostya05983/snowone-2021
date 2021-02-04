package ru.kontur.kinfra.client

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlow
import ru.kontur.kinfra.events.Event
import ru.kontur.kinfra.events.EventSourceable
import kotlin.reflect.KClass

class ServerSentEventClient<T : Event>(
    private val path: String,
    private val subscriptionPath: String,
    private val kClass: KClass<T>
) : EventSourceable<T> {
    private val objectMapper = createObjectMapper()

    override fun events(resumeAfter: String?): Flow<T> {
        val webClient = WebClient.create(path)
        val subscription = resumeAfter?.let { "${subscriptionPath}?id=${it}" } ?: subscriptionPath
        return webClient.get().uri(subscription)
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlow<ServerSentEvent<String>>().mapNotNull {
                it.data()?.let {
                    objectMapper.readValue(it, kClass.java)
                }
            }
    }
}