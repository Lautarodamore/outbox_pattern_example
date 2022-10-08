package domain

import com.nbottarini.asimov.json.Json
import com.nbottarini.asimov.json.values.JsonObject
import java.time.LocalDateTime
import java.util.UUID

class Event(
    val id: UUID = UUID.randomUUID(),
    val occurredOn: LocalDateTime = LocalDateTime.now(),
    val type: String,
    val data: String,
) {
    fun serialized(): JsonObject {
        return Json.obj("type" to type, "data" to data)
    }
}