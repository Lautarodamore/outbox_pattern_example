package infrastructure

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class DatabaseOutboxMessages(private val database: Database) {
    fun add(outboxMessage: OutboxMessage) {
        database.statement("INSERT INTO outbox_messages (id, occurredOn, type, data) VALUES ('${outboxMessage.id}', '${outboxMessage.occurredOn.formatAsISO8601()}', '${outboxMessage.type}', '${outboxMessage.data}');")
    }

    fun update(outboxMessage: OutboxMessage) {
        database.statement("UPDATE outbox_messages set occurredon = '${outboxMessage.occurredOn.formatAsISO8601()}', type = '${outboxMessage.type}', data = '${outboxMessage.data}', processeddate = '${outboxMessage.processedDate?.formatAsISO8601()}' WHERE id = '${outboxMessage.id}';")
    }

    private fun LocalDateTime.formatAsISO8601(): String {
        return this.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    fun parseISO8601(value: String?, localOffset: ZoneOffset = ZoneOffset.UTC): LocalDateTime? {
        if (value == null) return null
        return OffsetDateTime.parse(value.replace(" ", "T") + "+00:00", DateTimeFormatter.ISO_DATE_TIME)
            .withOffsetSameInstant(localOffset)
            .toLocalDateTime()
    }

    fun getOutboxMessagesWithoutProcessing(): List<OutboxMessage> {
        val result = database.prepareStatement("SELECT * FROM outbox_messages WHERE processedDate is null;")
        val outboxMessages = mutableListOf<OutboxMessage>()
        while (result.next()) {
            outboxMessages.add(
                OutboxMessage(
                    UUID.fromString(result.getString("id")),
                    parseISO8601(result.getString("occurredon"))!!,
                    result.getString("type"),
                    result.getString("data"),
                    parseISO8601(result.getString("processeddate"))
                )
            )
        }
        return outboxMessages.toList()
    }
}