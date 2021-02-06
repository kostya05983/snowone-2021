package ru.kontur.kinfra.queue

import ru.kontur.kinfra.events.Event

interface TaskQueue<T : Event> {
    fun sender(): Sender<T>

    fun receiver(): Receiver<T>
}

