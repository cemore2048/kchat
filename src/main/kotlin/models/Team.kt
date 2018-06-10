package models

import org.jetbrains.exposed.sql.Table

object Team : Table() {
    val id = varchar("id", 36).primaryKey()
    val createdAt = date("createdAt")
    val updateAt = date("updateAt")

    val name = varchar("name", 30)
}