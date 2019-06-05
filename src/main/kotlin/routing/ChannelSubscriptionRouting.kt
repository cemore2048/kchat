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
/**
 * The ChannelSubscriptionRouting API is used for subscribing a user to a specific channel
 * These calls manage the relationship between the user and said channel
 */
object ChannelSubscriptionRouting {

    /**
     * Subscirbes a user to a channel
     *
     * @requiredParam userId the id of the user you want to subscribe to a channel
     * @requiredParam channelId the id of the channel you want to subscribe the user to
     */
    fun Route.createChannelSubscription() {
        post<Locations.CreateChannelSubscription> {
            val params = call.parameters
            Logger.info("Subscribe a user to a channel ")
            val requiredParams = listOf("userId", "channelId")
            val missingFields = RoutingUtil.getMissingFields(requiredParams, params)

            if (missingFields.isNotEmpty()) {
                val response = missingFields.joinToString(separator = ", ")
                call.respond(CreateChannelSubscriptionResponse("failed", "Missing Fields: $response", null))
            } else {
                val channelId: String? = ChannelSubscriptionStore.createChannelSubscription(params)
                call.respond(CreateChannelSubscriptionResponse("success", "Successfully subscribed user to channel", channelId))
            }
        }

    }

    /**
     * Gets a ll channel subscriptions
     *
     * TODO did we forget to link this to a specific user?
     */
    fun Route.getAllSubscriptions() {
        get<Locations.GetAllSubscriptions> {
            val subscriptions: List<ChannelSubscriptionObj> = ChannelSubscriptionStore.getAllChannelSubcriptions()
            call.respond(ListChannelSubscription("success", "Successfully retrieved all the subscriptions", subscriptions))
        }
    }
}