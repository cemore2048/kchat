import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import models.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext


object DatabaseFactory {

    private val dispatcher: CoroutineContext

    init {
        dispatcher = newFixedThreadPoolContext(5, "database-pool")
    }

    fun init() {
        Database.connect(hikari())
        transaction {
            create(User, Channel, Message, ChannelSubscription, Team)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig("/hikari.properties")
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
            withContext(dispatcher) {
                transaction { block() }
            }
}