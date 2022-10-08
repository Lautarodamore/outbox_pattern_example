package infrastructure

import domain.Event
import domain.EventPublisher

class RabbitMQPublisher: EventPublisher {
    override fun publish(event: Event) {
        TODO("Not yet implemented")
    }
}