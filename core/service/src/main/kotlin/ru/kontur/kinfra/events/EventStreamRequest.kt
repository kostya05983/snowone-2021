package ru.kontur.kinfra.events

data class EventStreamRequest(
    val resumeEventId: String?
)