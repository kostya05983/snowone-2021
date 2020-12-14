package ru.kontur.kinfra

import reactor.core.publisher.Flux

interface EventSourceable<T> {
    fun events(resumeAfter: String? = null): Flux<out T>
}