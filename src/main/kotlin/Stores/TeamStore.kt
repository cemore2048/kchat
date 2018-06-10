package Stores

import io.ktor.http.Parameters
import models.Team
import models.Team.createdAt
import models.Team.id
import models.Team.name
import models.Team.updateAt
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime

data class TeamObj (
        var id: String,
        var name: String,
        var createdAt: String,
        var updateAt: String
)

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
    suspend fun getAll() : List <TeamObj>{
        return getAll<TeamObj> {
            TeamObj(
                    it[id],
                    it[name],
                    it[createdAt].toString(),
                    it[updateAt].toString()
            )
        }
    }
}