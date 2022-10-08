package infrastructure

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class Database {
    private val connection = DriverManager.getConnection("jdbc:postgresql://localhost:5532/outbox", "postgres", "1234")
    val users = DatabaseUsers(this)
    val outboxMessages = DatabaseOutboxMessages(this)

    fun transactional(block: Database.() -> Unit) {
        try {
            connection.autoCommit = false
            block()
            connection.commit()
        } catch (e: Throwable) {
            connection.rollback()
        }
    }

    fun statement(sql: String) {
        connection.prepareStatement(sql).execute()
    }

    fun prepareStatement(sql: String): ResultSet {
        return connection.prepareStatement(sql).executeQuery()
    }

    fun createUsersTable() {
        if (connection.existsTable("users")) return
        this.statement("CREATE TABLE users (id SERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL, email VARCHAR(100) NOT NULL, age INTEGER NOT NULL);")
    }

    fun createOutboxMessagesTable() {
        if (connection.existsTable("outbox_messages")) return
        this.statement("CREATE TABLE outbox_messages (id VARCHAR(100) NOT NULL, occurredOn TIMESTAMP NOT NULL, type VARCHAR(100) NOT NULL, data VARCHAR(255) NOT NULL, processedDate TIMESTAMP);")
    }

    private fun Connection.existsTable(table: String): Boolean {
        val result = this.prepareStatement("SELECT EXISTS (SELECT FROM pg_tables WHERE schemaname = 'public' AND tablename = '$table');").executeQuery()
        while (result.next()) { return result.getBoolean("exists") }
        return false
    }
}

