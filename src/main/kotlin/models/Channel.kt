package models

import org.jetbrains.exposed.sql.Table

object Channel : Table() {
    val id = varchar("id", 36).primaryKey()
    val createdAt = date("createdAt")
    val updateAt = date("updateAt")
    val deleteAt = date("deleteAt")
    val teamId = varchar("teamId", 36)
    val type = varchar("type", 10)
    val displayName = varchar("displayName", 30)
    val name = varchar("name", 30)
    val header = varchar("header", 20)
    val purpose = varchar("purpose", 50)
    val lastPostAt = date("lastPostAt")
    val totalMsgCount = integer("totalMsgCount")
    val extraUpdateAt = date("extraUpdateAt")
    val creatorId = varchar("creatorId", 36)
}