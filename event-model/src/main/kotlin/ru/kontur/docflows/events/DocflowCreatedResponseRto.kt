package ru.kontur.docflows.events

import ru.kontur.kinfra.queue.Task
import java.util.*

class DocflowCreatedResponseRto(
    override val traceId: String,
    override val correlationId: String
) : Task()