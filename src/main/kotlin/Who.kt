import io.ktor.application.*
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
        DatabaseFactory.init()

    val server = embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                call.respondText("Hello world", ContentType.Text.Plain)
            }

            get("/demo") {
                call.respondText {"Hello Worldd"}
            }
        }
    }
    server.start(wait = true)
}

