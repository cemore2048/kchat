package models

import org.jetbrains.exposed.sql.Table

object User : Table() {
    val id = varchar("id", 36).primaryKey()
    val firstName = varchar("firstName", 50)
    val lastName = varchar("lastName", 50)
    val createdAt = datetime("createdAt")
    val username = varchar("username", 64)
    val password = varchar("password", 72)
    val email = varchar("email", 128)
    //val emailVerified = bool("emailVerified")
    //val position = varchar("position", 20)
    //val lastPasswordUpdate = date("lastPasswordUpdate")
}


