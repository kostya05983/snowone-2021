package ru.kontur.kinfra.queue.impl

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.kontur.kinfra.events.Event
import ru.kontur.kinfra.queue.TaskSerializer
import java.nio.charset.StandardCharsets

class JsonSerializer<T: Event>(private val clazz: Class<*>) : TaskSerializer<T> {
    private val objectMapper: ObjectMapper = jacksonObjectMapper().apply {
        findAndRegisterModules()
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        registerModule(JavaTimeModule())
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    override val contentType: String = "application/json"

    override fun serialize(task: T): ByteArray = objectMapper.writeValueAsString(task)
        .toByteArray(StandardCharsets.UTF_8)

    @Suppress("UnsafeCast")
    override fun deserialize(task: ByteArray): T = objectMapper.readValue(task, clazz) as T
}
