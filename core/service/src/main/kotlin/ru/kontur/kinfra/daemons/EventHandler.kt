package ru.kontur.kinfra.daemons

import ru.kontur.kinfra.events.Event

interface EventHandler<T : Event> {
    suspend fun handle(item: T)
}