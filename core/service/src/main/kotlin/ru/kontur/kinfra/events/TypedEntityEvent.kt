package ru.kontur.kinfra.events

interface TypedEntityEvent : Event {
    val type: EntityType
}

enum class EntityType {
    USERS
}