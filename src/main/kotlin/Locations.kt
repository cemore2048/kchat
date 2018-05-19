import io.ktor.locations.Location
import org.joda.time.DateTime

class Locations {
    @Location("/register_user")
    data class Register(val username: String = "",
                        val firstName: String = "",
                        val lastName: String = "",
                        val email: String = "",
                        val password: String = "",
                        val createdAt: DateTime? = null)

    @Location("/users")
    data class GetAllUsers(val empty: String = "")

    @Location("/channels")
    data class Channels(val name: String = "")

    @Location("/channels/{uuid}")
    data class GetChannel(val uuid: String)

    @Location("/channels/{channelUuid}/subscribe/{userUuid}")
    data class CreateChannelSubscription(val channelUuid: String, val userUuid: String)
}