import org.jetbrains.exposed.sql.Table

object Users: Table() {
    val id = varchar("id", 10).primaryKey()
    val firstName = varchar("firstName", length = 50)
    val lastName = varchar("lastName", length = 50)
    val createdAt = date("createdAt")
    val username = varchar("username", length = 64)
    val password = varchar("password", length = 72)
    val email = varchar("email", length = 128)
    val emailVerified = bool("emailVerified")
    val position = varchar("position", length = 20)
    val lastPasswordUpdate = date("lastPasswordUpdate")
}
