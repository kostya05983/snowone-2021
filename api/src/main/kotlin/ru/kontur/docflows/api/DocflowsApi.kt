package ru.kontur.docflows.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DocflowsApi

fun main(args: Array<String>) {
    runApplication<DocflowsApi>()
}