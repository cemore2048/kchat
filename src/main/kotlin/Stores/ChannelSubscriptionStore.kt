package Stores

import models.ChannelSubscription
import io.ktor.http.Parameters
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import java.util.*

object ChannelSubscriptionStore {
    suspend fun createChannelSubscription(params: Parameters): String? {
        val uuid = UUID.randomUUID().toString()

        DatabaseFactory.dbQuery {
            ChannelSubscription.insert {
                it[userId] = params["userId"]!!
                it[channelId] = params["channelId"]!!

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
}