import infrastructure.Database

class Scenario(private val dataBase: Database) {
    fun createTables() {
        dataBase.createUsersTable()
        dataBase.createOutboxMessagesTable()
    }
}