package ru.kontur.kinfra.states

interface StateStorage<T> {
    suspend fun saveState(state: T)

    suspend fun getState(): T
}