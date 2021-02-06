package ru.kontur.docflows.events

import ru.kontur.kinfra.queue.Task
import java.util.*

class DocflowCreatedResponseRto(
    override val traceId: UUID,
    override val correlationId: String
) : Task()