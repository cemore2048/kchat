import io.ktor.http.Parameters
import models.User
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import java.util.*

object UserStore {

    suspend fun registerUser(params: Parameters) {
        if (isNewUser(params)) {
            DatabaseFactory.dbQuery {
                User.insert {
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

    suspend fun isNewUser(params: Parameters): Boolean {
        val user = DatabaseFactory.dbQuery {
            User.select { User.email.eq(params["email"]!!) }
        }.singleOrNull()

        return user == null
    }

}