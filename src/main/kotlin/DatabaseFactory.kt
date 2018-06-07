import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlinx.coroutines.experimental.withContext
import models.Channel
import models.ChannelSubscription
import models.Message
import models.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.experimental.CoroutineContext


object DatabaseFactory {

    private val dispatcher: CoroutineContext

    init {
        dispatcher = newFixedThreadPoolContext(5, "database-pool")
    }

    fun init() {
        Database.connect(hikari())
        transaction {
            create(User, Channel, Message, ChannelSubscription)
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