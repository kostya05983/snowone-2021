package ru.kontur.kinfra

open class DatabaseEvent<ACTION>(
    var traceId: String,
    var actions: List<ACTION>
)