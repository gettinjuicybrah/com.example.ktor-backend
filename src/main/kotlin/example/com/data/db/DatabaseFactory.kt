package example.com.data.db

import io.ktor.server.config.*
import java.sql.Connection
import java.sql.DriverManager

object DatabaseFactory {
    private var connection: Connection? = null

    fun init(config: ApplicationConfig) {
        val jdbcURL = config.property("database.jdbcURL").getString()
        val driverClassName = config.property("database.driverClassName").getString()
        val user = config.property("database.user").getString()
        val password = config.property("database.password").getString()

        try {
            Class.forName(driverClassName)
            connection = DriverManager.getConnection(jdbcURL, user, password)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getConnection(): Connection {
        return connection ?: throw IllegalStateException("Database not initialized")
    }
}