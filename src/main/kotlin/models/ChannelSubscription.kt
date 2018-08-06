package models

import org.jetbrains.exposed.sql.Table

object ChannelSubscription : Table() {
    // dynamic
    val id = varchar("id", 36).primaryKey()
    val createdAt = date("createdAt")
    val updateAt = date("updateAt")

    val userId = (varchar("userUuid", 36) references User.id)
    val channelId = (varchar("channelUuid", 36) references Channel.id)

}