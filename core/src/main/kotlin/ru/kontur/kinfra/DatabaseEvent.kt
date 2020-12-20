package ru.kontur.kinfra

abstract class DatabaseEvent<ACTION> {
    abstract var traceId: String
    abstract var actions: List<ACTION>
}