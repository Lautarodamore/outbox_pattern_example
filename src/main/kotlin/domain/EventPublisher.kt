package domain

interface EventPublisher {
    fun publish(event: Event)
}