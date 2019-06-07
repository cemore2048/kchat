package routing

import Locations
import Logger
import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import stores.TeamStore

data class ListResponse<T>(val status: String, val reason: String, val data: List<T>?)
data class CreateResponse(val status: String, val reason: String, val id: String?)
data class DeleteResponse(val status: String, val reason: String, val id: String?)

/**
 * The concept of a "Team" is the organization that your chat is a part of
 */
object TeamRouting {

    /**
     *  This is used to create the Team
     *
     *  @requiredParam name the name of the team
     */
    fun Route.createTeam() {
        post<Locations.Teams> {
            val params = call.receive<Parameters>()
            Logger.info("Create a Team ")
            val requiredParams = listOf("name")
            val missingFields = RoutingUtil.getMissingFields(requiredParams, params)

            if (missingFields.isNotEmpty()) {
                val response = missingFields.joinToString(separator = ", ")
                call.respond(CreateResponse("failed", "Missing Fields: $response", null))
            } else {
                val teamId: String? = TeamStore.createTeam(params)
                call.respond(CreateResponse("success", "Successfully created a Team", teamId))
            }
        }

        /**
         *  This gets a list of all the created teams
         */
        get<Locations.Teams> {
            val teams = TeamStore.getAll();
            call.respond(ListResponse("success", "Successfully retrieved all Teams", teams))
        }
    }

    /**
     * This gets one specific team
     *
     * @requiredParam uuid the id of the team you're trying to get
     */
    fun Route.getTeam() {
        get<Locations.Team> {
            val uuid = call.parameters["uuid"]
            val team = TeamStore.get(uuid)
            call.respond(CreateResponse("success", "Successfully retrieved a channel", team))
        }
    }
}