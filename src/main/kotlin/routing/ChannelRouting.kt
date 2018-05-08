package routing

import Stores.ChannelStore
import com.sun.media.jfxmedia.logging.Logger
import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

data class CreateChannelResponse(val status: String, val reason: String, val channelId: String?)

object ChannelRouting {
    fun Route.createChannel() {
        post<Locations.CreateChannel> {
            val params = call.receive<Parameters>()
            Logger.logMsg(Logger.INFO, "Create a channel ")
            val requiredParams = listOf("creatorId", "teamId", "type", "displayName", "name", "header", "purpose")
            val missingFields = mutableListOf<String>()
            for (paramKey in requiredParams) {
                if (params.get(paramKey).isNullOrBlank()) {
                    missingFields.add(paramKey)
                }
            }
            if (missingFields.isNotEmpty()) {
                val response = missingFields.joinToString(separator = ", ")
                call.respond(CreateChannelResponse("failed", "Missing Fields: $response", null))
            } else {
                val channelId: String? = ChannelStore.createChannel(params)
                call.respond(CreateChannelResponse("success", "Successfully created a channel", channelId))
            }
        }
    }

    fun Route.getChannel() {
        get<Locations.GetChannel> {
            val uuid = call.parameters["uuid"]
            val x = ChannelStore.getChannel(uuid)
            call.respond(CreateChannelResponse("success", "Successfully retrieved a channel", x))
        }
    }
}