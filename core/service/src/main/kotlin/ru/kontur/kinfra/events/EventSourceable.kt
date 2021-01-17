package ru.kontur.kinfra.events

import kotlinx.coroutines.flow.Flow

interface EventSourceable<T> {
    fun events(resumeAfter: String? = null): Flow<T>
}