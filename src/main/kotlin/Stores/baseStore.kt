package Stores

import DatabaseFactory
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import java.util.*

abstract class BaseStore<T : Table>(val model: T){

    abstract suspend fun get(uuid : String?) : String?

    suspend fun create(callback: (Pair<InsertStatement<Number>, String>) -> Unit): String? {
        val uuid = UUID.randomUUID().toString()
        DatabaseFactory.dbQuery {
            model.insert{
                //TODO undo this Pair couldn't figure out a different way
                callback(Pair(it, uuid))
                //TODO do uuid assignment, createdAt, created
            }
        }
        return get(uuid)
    }
    suspend fun get(callback: () -> String? ): String? {
        return DatabaseFactory.dbQuery {
            callback()
        }
    }

//    suspend fun _getAll() {
//        return DatabaseFactory.dbQuery {
//
//        }
//    }
}