package stores

import DatabaseFactory
import io.ktor.http.Parameters
import models.Message
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import java.util.*

object MessageStore {

    suspend fun create(params: Parameters): String? {
        var uuid = ""
        DatabaseFactory.dbQuery {
            uuid = (Message.insert {
                it[id] = UUID.randomUUID().toString()
                it[createdAt] = DateTime.now()
                it[updateAt] = DateTime.now()
                it[channelId] = params["channelId"]!!
                //it[channelDisplayName] = params["channelDisplayName"]!!
                //it[channelType] = params["channelType"]!!
                it[userId] = params["userId"]!!
                it[payload] = params["payload"]!!
                it[postType] = params["postType"]!!
            } get Message.id)
        }
        return uuid
    }

    suspend fun delete(uuid: String): String? {
        return DatabaseFactory.dbQuery {
            Message.deleteWhere {
                Message.id eq uuid
            }
            return@dbQuery uuid
        }
    }

    private suspend fun getMessage(uuid: String): String? {
        return DatabaseFactory.dbQuery {
            Message.select { Message.id eq uuid }.takeIf { !it.empty() }?.map { it[Message.id] }?.get(0)
        }
    }
}