import org.jetbrains.exposed.sql.Table

object Users: Table() {
    val id = varchar("id", 10).primaryKey()
    val firstName = varchar("firstName",50)
    val lastName = varchar("lastName", 50)
    val createdAt = date("createdAt")
    val username = varchar("username", 64)
    val password = varchar("password", 72)
    val email = varchar("email", 128)
    val emailVerified = bool("emailVerified")
    val position = varchar("position", 20)
    val lastPasswordUpdate = date("lastPasswordUpdate")
}

object Message: Table() {
    val id = varchar("id", 10)
    val channelId = varchar("channelId", 50).primaryKey()
    val channelDisplayName = varchar("channelDisplayName",  50)
    val channelType = varchar("channelType", 50)
    val userId = varchar("userId", 10)
    val postCreatedAt = integer("postCreatedAt")
    val payload = text("payload")
    val postType = varchar("postTYpe", 20)
}

object Channel: Table() {
    val id = varchar("id", 10).primaryKey()
    val createdAt = date("createdAt")
    val updateAt = date("updateAt")
    val deleteAt = date("deleteAt")
    val teamId = varchar("teamId", 10)
    val type = varchar("type", 10)
    val displayName = varchar("displayName", 30)
    val name = varchar("name", 30)
    val header = varchar("header", 20)
    val purpose = varchar("purpose", 50)
    val lastPostAt = date("lastPostAt")
    val totalMsgCount = integer("totalMsgCount")
    val extraUpdateAt = date("extraUpdateAt")
    val creatorId = varchar("creatorId", 10)
}

