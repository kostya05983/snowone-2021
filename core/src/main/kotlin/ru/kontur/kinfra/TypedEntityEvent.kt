package ru.kontur.kinfra

interface TypedEntityEvent : Event {
    val type: EntityType
}

enum class EntityType {
    USERS
}