package models

import org.jetbrains.exposed.sql.Table

object Team : Table() {
    val id = varchar("id", 36).primaryKey()
    val createdAt = Channel.date("createdAt")
    val updateAt = Channel.date("updateAt")

    val name = varchar("name", 30)
}