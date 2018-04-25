import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.Route

object Routing {
    fun Route.register() {
        post<Locations.Register> {
            //this obviously needs to have some checks.
            val params = call.receive<Parameters>()

            if (params["password"]!!.contains("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                call.respondText("Minimum eight characters, at least one letter, one number and one special character")
            }

            UserStore.registerUser(params)
            call.respondText("Successfully added user")
        }
    }

}