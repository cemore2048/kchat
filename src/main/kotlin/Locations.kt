import io.ktor.locations.Location

class Locations {

    @Location("/register_user")
    data class Register(val username: String = "",
                        val firstName: String = "",
                        val lastName: String = "",
                        val email: String = "",
                        val password: String = "")
}