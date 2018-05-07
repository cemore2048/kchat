package routing

import Stores.ChannelStore
import com.sun.media.jfxmedia.logging.Logger
import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import models.Channel


data class CreateChannelResponse(val status: String, val reason: String, val channelId: String?)


object ChannelRouting {
    fun Route.createChannel() {
        post<Locations.CreateChannel> {
            val params = call.receive<Parameters>()
            Logger.logMsg(Logger.INFO, "Create a channel ")
            val requiredParams = listOf("creatorId","teamId","type","displayName","name","header","purpose")
            val missingFields =  mutableListOf<String>()

            for (paramKey in requiredParams) {
               if(params.get(paramKey).isNullOrBlank()){
                   missingFields.add(paramKey)
               }
            }
            if(!missingFields.isEmpty()){
                val response = missingFields.joinToString (separator = ", ")
                call.respond(CreateChannelResponse("failed", "Missing Fields: $response", null))
            } else {
                val channelId: String? = ChannelStore.createStore(params)?.map { it[Channel.id] }?.get(0)
                call.respond(CreateChannelResponse("failed", "Successfully created a channel", channelId))
            }
        }
    }
}