import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authenticate
import io.ktor.auth.basic
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.locations.Location
import io.ktor.locations.Locations
import io.ktor.locations.location
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.SessionTransportTransformerMessageAuthentication
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.util.hex
import routing.CreateUserResponse
import routing.UserRouting.register
import routing.ChannelRouting.createChannel
import routing.ChannelRouting.getChannel
import routing.ChannelSubscriptionRouting.createChannelSubscription
import routing.UserRouting.getUsers
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

data class UserSession(val userId: String)

val hashKey = hex("6819b57a326945c1968f45236589")

@Location("/manual")
class Manual

fun Application.mainModule() {
    DatabaseFactory.init()
    install(DefaultHeaders)
    install(Locations)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(Sessions) {
        cookie<UserSession>("SESSION") {
            transform(SessionTransportTransformerMessageAuthentication(hashKey))
        }
    }
    install(Authentication) {
        basic("kchatAuth1") {
            realm = "Kchat Server"
            validate {
                when {
                    it.password == it.name -> UserIdPrincipal(it.name)
                    else -> null
                }
            }
        }
    }

    data class AuthenticateUserResponse(val status: String)
    routing {
        register()
        getUsers()
        createChannel()
        getChannel()
        createChannelSubscription()
        location<Manual> {
            authenticate("kchatAuth1") {
                get {
                    call.respond(AuthenticateUserResponse("some crap for now"))
                }
            }
        }
    }
}

val hmacKey = SecretKeySpec(hashKey, "HmacSHA1")

fun hash(password: String): String {
    val hmac = Mac.getInstance("HmacSHA1")
    hmac.init(hmacKey)
    return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
}


fun main(args: Array<String>) {
    embeddedServer(Netty, 8080, module = Application::mainModule).start(wait = true)
}


