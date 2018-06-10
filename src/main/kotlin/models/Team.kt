package models

import org.jetbrains.exposed.sql.Table


object Team : BaseTable() {
    val name = varchar("name", 30)
}