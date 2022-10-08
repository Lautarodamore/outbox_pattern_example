package app

import com.nbottarini.asimov.json.Json
import domain.User
import infrastructure.Database
import infrastructure.OutboxMessage

class RegisterUser(private val dataBase: Database) {
    fun execute(name: String, email: String, age: Int) {
        val user = User(name, email, age)
        val outboxMessage = OutboxMessage(type = "NewPersonRegistered", data = Json.obj("email" to email).toString())
        dataBase.transactional {
            users.add(user)
            outboxMessages.add(outboxMessage)
        }
    }
}
