package Stores

import DatabaseFactory
import models.BaseTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.joda.time.DateTime
import java.util.*

abstract class BaseStore<T : BaseTable>(val model: T){
    suspend fun create(callback: (Pair<InsertStatement<Number>, String>) -> Unit): String? {
        val uuid = UUID.randomUUID().toString()
        DatabaseFactory.dbQuery {
            model.insert{
                //TODO undo this Pair couldn't figure out a different way
                callback(Pair(it, uuid))
                it[id] = uuid
                it[createdAt] = DateTime.now()
                it[updateAt] = DateTime.now()

            }
        }
        return get(uuid)
    }
    suspend fun get(uuid: String?): String? {
        return DatabaseFactory.dbQuery {
            model.select {
                model.id.eq(uuid!!)
            }.takeIf { !it.empty() }?.map { it[model.id] }?.get(0)
        }
    }

    suspend fun <L>getAll(callback: (items: ResultRow) -> L) : List <L>{
        return DatabaseFactory.dbQuery {
            model.selectAll().map {
                return@map callback(it)
            }
        }
    }
    suspend fun updateById(id : String , callback2: (x : T) -> UpdateStatement){
        return DatabaseFactory.dbQuery {
            model.update({ model.id eq id!! })
            {
                callback2(this)
            }
        }
    }
}