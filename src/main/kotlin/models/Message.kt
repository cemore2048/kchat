package models

object Message : BaseTable() {
    val channelId = varchar("channelId", 50)
    //val channelDisplayName = varchar("channelDisplayName", 50)
    //val channelType = varchar("channelType", 50)
    val userId = varchar("userId", 36)
    val payload = text("payload")
    val postType = varchar("postType", 20)

}