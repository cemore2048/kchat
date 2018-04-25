import org.jetbrains.exposed.sql.insert
import org.joda.time.DateTime
import io.ktor.http.Parameters
import java.util.*

object UserStore {

    suspend fun registerUser(params: Parameters) {
        DatabaseFactory.dbQuery {
            Users.insert {
                it[password] = params["password"]!!
                it[username] = params["username"]!!
                it[firstName] = params["firstName"]!!
                it[lastName] = params["lastName"]!!
                it[email] = params["email"]!!
                it[id] = UUID.randomUUID().toString()
                it[createdAt] = DateTime.now()
            }
        }
    }

}