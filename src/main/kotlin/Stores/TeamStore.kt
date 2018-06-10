package Stores

import io.ktor.http.Parameters
import models.Team
import models.Team.createdAt
import models.Team.id
import models.Team.name
import models.Team.updateAt
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime

object TeamStore : BaseStore<Team>(Team) {
    suspend fun create(params: Parameters): String? {
        return create  {
                it.first[name] = params["name"]!!
                it.first[id] = it.second
                it.first[createdAt] = DateTime.now()
                it.first[updateAt] = DateTime.now()

        }

    }
    override suspend fun get(uuid: String?): String? {
        return get{
            Team.select {
                Team.id.eq(uuid!!)
            }.takeIf { !it.empty() }?.map { it[Team.id] }?.get(0)
        }
    }
}