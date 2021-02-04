package ru.kontur.kinfra.client

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.web.util.DefaultUriBuilderFactory
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import ru.kontur.kinfra.events.Event
import ru.kontur.kinfra.events.EventSourceable
import ru.kontur.kinfra.events.EventStreamRequest
import java.net.URI
import kotlin.reflect.KClass

class RSocketEventClient<T : Event>(
    private val host: URI,
    private val path: String,
    private val route: String,
    private val rSocketStrategies: RSocketStrategies,
    private val eventType: KClass<T>
) : EventSourceable<T> {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun events(resumeAfter: String?): Flow<T> {
        return createRSocketRequester().flatMapMany {
            logger.info("Connect to rsocket route with id $resumeAfter")

            it.route(route)
                .data(EventStreamRequest(resumeAfter))
                .retrieveFlux(eventType.java)
        }.asFlow()
    }

    private fun createRSocketRequester(): Mono<RSocketRequester> {
        val url = DefaultUriBuilderFactory()
            .uriString(host.toASCIIString())
            .scheme("ws")
            .path(path)
            .build()

        return RSocketRequester.builder()
            .rsocketStrategies(rSocketStrategies)
            .connectWebSocket(url)
    }
}