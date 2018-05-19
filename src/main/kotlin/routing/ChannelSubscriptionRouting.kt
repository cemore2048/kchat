package routing

import Stores.ChannelSubscriptionObj
import Stores.ChannelSubscriptionStore
import com.sun.media.jfxmedia.logging.Logger
import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

data class CreateChannelSubscriptionResponse(val status: String, val reason: String, val channelVal: String?)
data class ListChannelSubscription(val status: String, val reason: String, val channelId: List<ChannelSubscriptionObj>?)

object ChannelSubscriptionRouting {
    fun Route.createChannelSubscription() {
        post<Locations.CreateChannelSubscription> {
            val params = call.parameters
            Logger.logMsg(Logger.INFO, "subscribe a user to a channel ")
            val requiredParams = listOf("userUuid", "channelUuid")
            val missingFields: List<String> =
                    requiredParams.filter { param ->
                        params[param].isNullOrBlank()
                    }
            if (missingFields.isNotEmpty()) {
                val response = missingFields.joinToString(separator = ", ")
                call.respond(CreateChannelSubscriptionResponse("failed", "Missing Fields: $response", null))
            } else {
                val channelId: String? = ChannelSubscriptionStore.createChannelSubscription(params)
                call.respond(CreateChannelSubscriptionResponse("success", "Successfully subscribed user to channel", channelId))
            }
        }

    }
    fun Route.getAllSubscriptions(){
        get<Locations.GetAllSubscriptions>{
            val subscriptions: List<ChannelSubscriptionObj> = ChannelSubscriptionStore.getAllChannelSubcriptions()
            call.respond(ListChannelSubscription("success", "Successfully retrieved all the subscriptions", subscriptions))
        }
    }
}