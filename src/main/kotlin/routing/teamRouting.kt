package routing

import Stores.TeamStore
import Stores.UserStore
import Stores.UsersObj
import com.sun.media.jfxmedia.logging.Logger
import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

object teamRouting{
    fun Route.createTeam() {
        post<Locations.Teams> {
            val params = call.receive<Parameters>()
            Logger.logMsg(Logger.INFO, "Create a Team ")
            val requiredParams = listOf("name")
            val missingFields: List<String> =
                    requiredParams.filter { param ->
                        params[param].isNullOrBlank()
                    }
            if (missingFields.isNotEmpty()) {
                val response = missingFields.joinToString(separator = ", ")
                call.respond(CreateChannelResponse("failed", "Missing Fields: $response", null))
            } else {
                val teamId: String? = TeamStore.create(params)
                call.respond(CreateChannelResponse("success", "Successfully created a Team", teamId))
            }
        }
        get<Locations.Teams>{
            call.respond(ListUserResponse("success", "Successfully retrieved all Teams", null))
        }
    }
}