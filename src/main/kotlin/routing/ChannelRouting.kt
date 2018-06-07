package routing

import Stores.*
import com.sun.media.jfxmedia.logging.Logger
import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route


data class CreateChannelResponse(val status: String, val reason: String, val channelId: String?)
data class ListChannelResponse(val status: String, val reason: String, val channelId: List<ChannelObj>?)
data class ChannelMem(val name: String, val id: String, val users: List<UserSmallObj>?)
data class ListUsersChannelsResponse(val status: String, val reason: String, val channel: ChannelMem?)

object ChannelRouting {
    fun Route.createChannel() {
        post<Locations.Channels> {
            val params = call.receive<Parameters>()
            Logger.logMsg(Logger.INFO, "Create a channel ")
            val requiredParams = listOf("creatorId", "teamId", "type", "displayName", "name", "header", "purpose")
            val missingFields: List<String> =
                    requiredParams.filter { param ->
                       params[param].isNullOrBlank()
                    }
            if (missingFields.isNotEmpty()) {
                val response = missingFields.joinToString(separator = ", ")
                call.respond(CreateChannelResponse("failed", "Missing Fields: $response", null))
            } else {
                val channelId: String? = ChannelStore.createChannel(params)
                call.respond(CreateChannelResponse("success", "Successfully created a channel", channelId))
            }
        }
        get<Locations.Channels>{
            val channels: List<ChannelObj> = ChannelStore.getAllChannels()
            call.respond(ListChannelResponse("success", "Successfully retrieved all channels", channels))
        }
    }

    fun Route.getChannel() {
        get<Locations.GetChannel> {
            val uuid = call.parameters["uuid"]
            val x = ChannelStore.getChannel(uuid)
            call.respond(CreateChannelResponse("success", "Successfully retrieved a channel", x))
        }
    }

    fun Route.getAllUsersForChannel(){
        get<Locations.GetAllUsersForChannel> {
            try { call.parameters["uuid"]!! } catch(e: NullPointerException){
                call.respond(ListUsersChannelsResponse("failed", "Missing uuid of channel ", null))
            }
            call.parameters["uuid"]?.let{
                val uuid = it
                Logger.logMsg(Logger.INFO, "Starting getting all users for channels")
                val channelUsers : List<ChannelUsers>? = ChannelSubscriptionStore.getUsersInChannel(uuid)
                if(channelUsers != null){
                    val users :List<UserSmallObj>? = channelUsers.map{ it.User }
                    val respObj = ChannelMem(channelUsers[0].name, channelUsers[0].id, users)
                    call.respond(ListUsersChannelsResponse("success", "Successfully retrieved a channel", respObj))
                }else{
                    call.respond(ListUsersChannelsResponse("success", "Successfully retrieved no channels", null))
                }
            }
        }
    }
}