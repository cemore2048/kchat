import Routing.register
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.DefaultHeaders
import io.ktor.locations.Locations
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun Application.mainModule() {
    DatabaseFactory.init()
    install(DefaultHeaders)
    install(Locations)
        routing {
            register()
        }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080, module = Application::mainModule).start(wait=true)
}


