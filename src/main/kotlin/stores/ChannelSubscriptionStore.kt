package stores

import DatabaseFactory
import io.ktor.http.Parameters
import models.Channel
import models.ChannelSubscription
import models.User
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.joda.time.DateTime
import java.util.*

data class UserSmallObj(
        val username: String,
        val id: String)

data class ChannelUsers(
        val name: String,
        val id: String,
        val User: UserSmallObj
)

data class ChannelSubscriptionObj(
        var userId: String,
        var channelId: String,
        var id: String,
        var createdAt: String,
        var updateAt: String
)

object ChannelSubscriptionStore {
    suspend fun createChannelSubscription(params: Parameters): String? {
        var uuid = ""

        DatabaseFactory.dbQuery {
            uuid = (ChannelSubscription.insert {
                it[userId] = params["userId"]!!
                it[channelId] = params["channelId"]!!

                it[id] = UUID.randomUUID().toString()
                it[createdAt] = DateTime.now()
                it[updateAt] = DateTime.now()
            } get ChannelSubscription.id)
        }
        return uuid
    }

    private suspend fun getChannelSubscription(uuid: String): String? {
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

    suspend fun getUsersInChannel(uuid: String): List<ChannelUsers>? {
        return DatabaseFactory.dbQuery {
            (User innerJoin ChannelSubscription innerJoin Channel).slice(User.username, User.id,
                    ChannelSubscription.id, ChannelSubscription.userId, ChannelSubscription.channelId,
                    Channel.id, Channel.name, Channel.creatorId).select { ChannelSubscription.channelId.eq(uuid!!) }.map {
                return@map ChannelUsers(
                        it[Channel.name],
                        it[Channel.id],
                        UserSmallObj(
                                it[User.username],
                                it[User.id]
                        )

                )
            }
        }
    }
}