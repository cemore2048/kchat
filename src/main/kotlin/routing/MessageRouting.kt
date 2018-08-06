package routing

import Locations
import com.sun.media.jfxmedia.logging.Logger
import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import models.Message
import stores.BaseStore
import stores.MessageStore


object MessageRouting : BaseStore<Message>(Message) {
    fun Route.createMessage() {
        post<Locations.CreateMessage> {
            val params = call.receive<Parameters>()
            Logger.logMsg(Logger.INFO, "Create message")

            val requiredParams = listOf("name")
            val missingFields: List<String> =
                    requiredParams.filter {
                        params[it].isNullOrBlank()
                    }
            if (missingFields.isNotEmpty()) {
                val response = missingFields.joinToString(separator = ", ")
                call.respond(CreateResponse("failed", "Missing Fields: $response", null))
            } else {
                val teamId: String? = MessageStore.create(params)
                call.respond(CreateResponse("success", "Successfully created a Team", teamId))
            }
        }
    }

    fun Route.deleteMessage() {

    }
}