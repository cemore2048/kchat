package routing

import Locations
import Logger
import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.locations.delete
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import models.Message
import stores.BaseStore
import stores.MessageStore




object MessageRouting : BaseStore<Message>(Message) {

    /*
     * Creates a message on the server that will then be delivered to a specified {@link Channel}
     *
     * @argument payload the message that is being delivered
     * @argument channelID the channel that the message will be delivered to
     * @argument userId the user that is sending the message
     */
    fun Route.createMessage() {
        post<Locations.CreateMessage> {
            val params = call.receive<Parameters>()
            Logger.info("Create message")

            val requiredParams = listOf("payload", "channelId", "postType", "userId")
            val missingFields = RoutingUtil.getMissingFields(requiredParams, params)
            if (missingFields.isNotEmpty()) {
                val response = missingFields.joinToString(separator = ",")
                call.respond(CreateResponse("failed", "Missing Fields: $response", null))
            } else {
                val teamId: String? = MessageStore.create(params)
                call.respond(CreateResponse("success", "Successfully created a Message", teamId))
            }
        }
    }


    /*
     * Deletes a message
     *
     * @argument id the message id
     */
    fun Route.deleteMessage() {
        delete<Locations.DeleteMessage> {
            val params = call.receive<Parameters>()
            Logger.info("Delete Message")

            val requiredParams = listOf("id")
            val missingFields = RoutingUtil.getMissingFields(requiredParams, params)

            if (missingFields.isNotEmpty()) {
                val response = missingFields.joinToString(separator = ",")
                call.respond(CreateResponse("failed", "Missing Fields: $response", null))
            } else {
                val messageId: String? = MessageStore.delete(params["id"]!!)
                call.respond(DeleteResponse("succes", "Deleted message", messageId))
            }
        }
    }
}