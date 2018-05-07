import io.ktor.http.Parameters
import models.User
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import java.util.*

object UserStore {
    suspend fun registerUser(params: Parameters): Query? {
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

            return getUser(params["email"])
        }
        return null
    }

    suspend fun isNewUser(params: Parameters): Boolean = getUser(params["email"]) == null

    private suspend fun getUser(email: String?): Query? {
        return DatabaseFactory.dbQuery {
            User.select { User.email.eq(email!!) }}.takeIf { !it.empty() }
    }
}
