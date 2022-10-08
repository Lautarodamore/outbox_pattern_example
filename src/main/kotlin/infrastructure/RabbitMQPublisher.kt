package infrastructure

import com.rabbitmq.client.ConnectionFactory
import domain.Event
import domain.EventPublisher

class RabbitMQPublisher: EventPublisher {
    private val queueName = "events"
    private val factory = ConnectionFactory().also { it.host = "localhost" }

    override fun publish(event: Event) {
        publishEvent(event)
    }

    private fun publishEvent(event: Event) {
        factory.newConnection().use {
            val channel = it.createChannel()
            channel.queueDeclare(queueName, false, false, false, null)
            val message = event.serialized().toString()
            channel.basicPublish("", queueName, null, message.toByteArray())
            println(" [x] Sent '$message'")
        }
    }
}