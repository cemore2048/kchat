package models

import org.jetbrains.exposed.sql.Table

abstract class BaseTable : Table() {
    val id = varchar("id", 36).primaryKey()
    val createdAt = date("createdAt")
    val updateAt = date("updateAt")
}