package Stores

import io.ktor.http.Parameters
import models.Channel
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import java.util.*

object ChannelStore {
    suspend fun createStore(params: Parameters): Query? {
            DatabaseFactory.dbQuery {
                Channel.insert {
                    it[creatorId] = params["creatorId"]!!
                    it[teamId] = params["teamId"]!!
                    it[type] = params["type"]!!
                    it[displayName] = params["displayName"]!!
                    it[name] = params["name"]!!
                    it[header] = params["header"]!!
                    it[purpose] = params["purpose"]!!

                    it[id] = UUID.randomUUID().toString()
                    it[createdAt] = DateTime.now()
                    it[updateAt] = DateTime.now()
                    it[totalMsgCount] = 0
                }
            }
        //TODO: just return what gets returned
        return getChannel(params["name"])
    }
    private suspend fun getChannel(name: String?): Query? {
        return DatabaseFactory.dbQuery {
            Channel.select { Channel.name.eq(name!!) }}.takeIf { !it.empty() }
    }
}