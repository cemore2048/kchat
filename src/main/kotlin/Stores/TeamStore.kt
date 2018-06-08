package Stores

import io.ktor.http.Parameters
import models.Team
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import java.util.*

object TeamStore {
    suspend fun createChannel(params: Parameters): String? {
        val uuid = UUID.randomUUID().toString()
        DatabaseFactory.dbQuery {
            Team.insert {
                it[name] = params["name"]!!
                it[id] = uuid
                it[createdAt] = DateTime.now()
                it[updateAt] = DateTime.now()
            }
        }
        return getTeam(uuid)
    }

    suspend fun getTeam(uuid: String?): String? {
        return DatabaseFactory.dbQuery {
            Team.select {
                Team.id.eq(uuid!!)
            }.takeIf { !it.empty() }?.map { it[Team.id] }?.get(0)
        }
    }
}