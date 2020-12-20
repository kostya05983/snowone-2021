package ru.kontur.kinfra.events

abstract class DatabaseEvent<ACTION> {
    abstract var traceId: String
    abstract var actions: List<ACTION>
}