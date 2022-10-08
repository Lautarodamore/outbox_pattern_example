package domain

import java.time.LocalDateTime
import java.util.UUID

class Event(
    val id: UUID = UUID.randomUUID(),
    val occurredOn: LocalDateTime = LocalDateTime.now(),
    val type: String,
    val data: String,
)