package infrastructure

import java.time.LocalDateTime
import java.util.UUID

class OutboxMessage(
    val id: UUID = UUID.randomUUID(),
    val occurredOn: LocalDateTime = LocalDateTime.now(),
    val type: String,
    val data: String,
    val processedDate: LocalDateTime? = null
) {
    fun processedAt(now: LocalDateTime): OutboxMessage {
        return OutboxMessage(id, occurredOn, type, data, now)
    }
}