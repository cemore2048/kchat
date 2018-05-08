package Stores

import DatabaseFactory
import io.ktor.http.Parameters
import models.Channel
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import java.util.*

object ChannelStore {
    suspend fun createChannel(params: Parameters): String? {
        val uuid = UUID.randomUUID().toString()
        DatabaseFactory.dbQuery {
            Channel.insert {
                it[creatorId] = params["creatorId"]!!
                it[teamId] = params["teamId"]!!
                it[type] = params["type"]!!
                it[displayName] = params["displayName"]!!
                it[name] = params["name"]!!
                it[header] = params["header"]!!
                it[purpose] = params["purpose"]!!

                it[id] = uuid
                it[createdAt] = DateTime.now()
                it[updateAt] = DateTime.now()
                it[totalMsgCount] = 0
            }
        }
        //TODO: just return what gets returned
        return getChannel(uuid)
    }

    suspend fun getChannel(uuid: String?): String? {
        return DatabaseFactory.dbQuery {
            Channel.select {
                Channel.id.eq(uuid!!)
            }.takeIf { !it.empty() }?.map { it[Channel.id] }?.get(0)
        }
    }
}