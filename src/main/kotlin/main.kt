import com.nbottarini.asimov.json.Json
import app.RegisterUser
import infrastructure.Database
import io.javalin.Javalin
import io.javalin.http.Context

fun main() {
    val dataBase = Database()
    val scenario = Scenario(dataBase)
    scenario.createTables()

    val httpApplication = HttpApplication(dataBase)
    httpApplication.start()
}

class HttpApplication(dataBase: Database) {
    private val PORT = 6061
    private val app = Javalin.create { config -> config.showJavalinBanner = false }
    private val createUser = RegisterUser(dataBase)

    init {
        app.post("/users", ::registerUser)
    }

    fun start() {
        app.start(PORT)
    }

    private fun registerUser(ctx: Context) {
        val json = Json.parse(ctx.body()).asObject()!!
        createUser.execute(json["name"]!!.asString()!!, json["email"]!!.asString()!!, json["age"]!!.asInt()!!)
    }
}

