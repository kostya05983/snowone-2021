package ru.kontur.kinfra

abstract class EntityEvent(
    override val id: String,
    val entityId: String
) : Event