package ru.kontur.kinfra.events

import reactor.core.publisher.Flux

interface EventSourceable<T> {
    fun events(resumeAfter: String? = null): Flux<out T>
}