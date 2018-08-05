package models

import org.jetbrains.exposed.sql.Table

object Channel : Table() {
    // dynamic
    val id = varchar("id", 36).primaryKey()
    val createdAt = date("createdAt")
    val updateAt = date("updateAt")

    val deleteAt = (date("deleteAt")).nullable()
    val lastPostAt = (date("lastPostAt")).nullable()
    val extraUpdateAt = (date("extraUpdateAt")).nullable()
    val totalMsgCount = integer("totalMsgCount")

    // payload defined
    val creatorId = varchar("creatorId", 36)
    val teamId = (varchar("teamId", 36) references Team.id)
    val type = varchar("type", 10)  // public, private, dm
    val displayName = varchar("displayName", 30)
    val name = varchar("name", 30)
    val header = varchar("header", 20)
    val purpose = varchar("purpose", 50)
}