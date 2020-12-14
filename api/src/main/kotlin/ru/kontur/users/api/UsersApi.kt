package ru.kontur.users.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UsersApi

fun main(args: Array<String>) {
    runApplication<UsersApi>()
}