package models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Date
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object ChannelSubscription : Table() {
    // dynamic
    val id = varchar("id", 36).primaryKey()
    val createdAt = date("createdAt")
    val updateAt = date("updateAt")

    val userId = (varchar("userUuid", 36) references User.id)
    val channelId = (varchar("channelUuid", 36) references Channel.id)

}