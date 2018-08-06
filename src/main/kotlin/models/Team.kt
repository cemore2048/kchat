package models


object Team : BaseTable() {
    val name = varchar("name", 30)
}