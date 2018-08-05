package stores

import DatabaseFactory
import io.ktor.http.Parameters
import models.Channel
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.joda.time.DateTime
import java.util.*

data class ChannelObj(val creatorId: String,
                      val teamId: String,
                      val type: String,
                      val displayName: String,
                      val name: String,
                      val header: String,
                      val purpose: String,
                      val id: String,
                      val createdAt: String,
                      val updatedAt: String,
                      val totalMsgCount: Int)

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

    suspend fun getAllChannels(): List<ChannelObj> {
        return DatabaseFactory.dbQuery {
            Channel.selectAll().map {
                return@map ChannelObj(
                        it[Channel.creatorId],
                        it[Channel.teamId],
                        it[Channel.type],
                        it[Channel.displayName],
                        it[Channel.name],
                        it[Channel.header],
                        it[Channel.purpose],
                        it[Channel.id],
                        it[Channel.createdAt].toString(),
                        it[Channel.updateAt].toString(),
                        it[Channel.totalMsgCount])
            }
        }
    }
}