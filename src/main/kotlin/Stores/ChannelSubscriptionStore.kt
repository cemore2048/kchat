package Stores

import models.ChannelSubscription
import io.ktor.http.Parameters
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.joda.time.DateTime
import java.util.*

data class ChannelSubscriptionObj (
        var userId: String,
        var channelId: String,
        var id: String,
        var createdAt: String,
        var updateAt: String
)
object ChannelSubscriptionStore {
    suspend fun createChannelSubscription(params: Parameters): String? {
        val uuid = UUID.randomUUID().toString()

        DatabaseFactory.dbQuery {
            ChannelSubscription.insert {
                it[userId] = params["userUuid"]!!
                it[channelId] = params["channelUuid"]!!

                it[id] = uuid
                it[createdAt] = DateTime.now()
                it[updateAt] = DateTime.now()
            }
        }
        return getChannelSubscription(uuid)
    }
    suspend private fun getChannelSubscription(uuid: String?): String? {
        return DatabaseFactory.dbQuery {
            ChannelSubscription.select {
                ChannelSubscription.id.eq(uuid!!)
            }.takeIf { !it.empty() }?.map { it[ChannelSubscription.id] }?.get(0)
        }
    }

    suspend fun getAllChannelSubcriptions(): List<ChannelSubscriptionObj> {
        return DatabaseFactory.dbQuery {
            ChannelSubscription.selectAll().map {
                return@map ChannelSubscriptionObj(
                    it[ChannelSubscription.userId],
                    it[ChannelSubscription.channelId],
                    it[ChannelSubscription.id],
                    it[ChannelSubscription.createdAt].toString(),
                    it[ChannelSubscription.updateAt].toString()
                )
            }
        }
    }
}