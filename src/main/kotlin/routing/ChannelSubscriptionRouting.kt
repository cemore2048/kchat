package routing

import Locations
import Logger
import io.ktor.application.call
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.response.respond
import io.ktor.routing.Route
import stores.ChannelSubscriptionObj
import stores.ChannelSubscriptionStore

data class CreateChannelSubscriptionResponse(val status: String, val reason: String, val channelVal: String?)
data class ListChannelSubscription(val status: String, val reason: String, val channelId: List<ChannelSubscriptionObj>?)

object ChannelSubscriptionRouting {
    fun Route.createChannelSubscription() {
        post<Locations.CreateChannelSubscription> {
            val params = call.parameters
            Logger.info("Subscribe a user to a channel ")
            val requiredParams = listOf("userId", "channelId")
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

    fun Route.getAllSubscriptions() {
        get<Locations.GetAllSubscriptions> {
            val subscriptions: List<ChannelSubscriptionObj> = ChannelSubscriptionStore.getAllChannelSubcriptions()
            call.respond(ListChannelSubscription("success", "Successfully retrieved all the subscriptions", subscriptions))
        }
    }
}