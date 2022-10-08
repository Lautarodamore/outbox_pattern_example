import domain.Event
import domain.EventPublisher
import infrastructure.Database
import infrastructure.RabbitMQPublisher
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

fun main() {
    val dataBase = Database()
    val eventPublisher = RabbitMQPublisher()
    val outboxProcessor = OutboxProcessor(dataBase, eventPublisher)
    Timer().schedule(outboxProcessor, 0, 200)
}

class OutboxProcessor(private val dataBase: Database, private val eventPublisher: EventPublisher): TimerTask() {
    override fun run() {
        val outboxMessages = dataBase.outboxMessages.getOutboxMessagesWithoutProcessing()
        outboxMessages.forEach {
            eventPublisher.publish(Event(type = it.type, data = it.data))
            dataBase.outboxMessages.update(it.processedAt(LocalDateTime.now()))
        }
    }
}
