import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.Route
import org.jetbrains.exposed.sql.insert
import org.joda.time.DateTime
import java.util.*

object Routing {
    fun Route.register() {
        post<Locations.Register> {
            //this obviously needs to have some checks.
            val params = call.receive<Parameters>()
            call.respondText("User ${params["username"]}")
            DatabaseFactory.dbQuery {
                Users.insert {
                    it[password] = params["password"]!!
                    it[username] = params["username"]!!
                    it[firstName] = params["firstName"]!!
                    it[lastName] = params["lastName"]!!
                    it[email] = params["email"]!!
                    it[id] = UUID.randomUUID().toString()
                    it[createdAt] = DateTime.now()
                }
            }
        }
    }
}