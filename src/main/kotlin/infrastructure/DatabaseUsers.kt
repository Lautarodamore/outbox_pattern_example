package infrastructure

import domain.User

class DatabaseUsers(private val database: Database) {

    fun add(user: User) {
        database.statement("INSERT INTO users (name, email, age) VALUES ('${user.name}', '${user.email}', ${user.age})")
    }
}