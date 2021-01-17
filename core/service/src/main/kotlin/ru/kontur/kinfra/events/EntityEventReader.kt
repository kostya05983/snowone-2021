package ru.kontur.kinfra.events

interface EntityEventReader<EVENT : DatabaseEvent<*>> {
    fun read(mongoEvent: MongoEvent): EVENT?
}