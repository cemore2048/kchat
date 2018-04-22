import io.ktor.locations.post
import io.ktor.routing.Route
import org.jetbrains.exposed.sql.insert

object Routing {
    fun Route.register() {
        post<Locations.Register> { register ->

            //this obviously needs to have some checks.

            DatabaseFactory.dbQuery {
                Users.insert {
                    it[password] = register.password
                    it[username] = register.username
                    it[firstName] = register.firstName
                    it[lastName] = register.lastName
                    it[email] = register.email
                }
            }
        }
    }
}