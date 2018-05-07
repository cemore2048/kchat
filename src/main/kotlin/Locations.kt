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

    @Location("/channel")
    data class CreateChannel(val username: String = "")
}