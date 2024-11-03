package example.com.data.repository

import example.com.data.db.DatabaseFactory
import example.com.models.User
import example.com.models.UserAuth
import java.sql.SQLException

object UserRepository {

    private fun checkCredentials(username: String, password: String): Boolean {
        val query = "SELECT 1 FROM USERS WHERE username = ? AND password = ?"
        try {
            val connection = DatabaseFactory.getConnection()
            val statement = connection.prepareStatement(query)
            statement.setString(1, username)
            statement.setString(2, password)
            statement.executeQuery().use { resultSet ->
                connection.close()
                statement.close()
                return resultSet.next()
            }
        } catch (e: SQLException) {

        }
        return false
    }

    fun getUserID(username: String): String {
        val query = "SELECT id FROM USERS WHERE username = ?"
        try {
            val connection = DatabaseFactory.getConnection()
            val statement = connection.prepareStatement(query)
            statement.setString(1, username)
            statement.executeQuery().use { resultSet ->
                connection.close()
                statement.close()
                return resultSet.getString("id")
            }
        } catch (e: SQLException) {
                return e.toString()
        }

    }


    fun tryRegister(user: UserAuth): Boolean{
        val username = user.username
        val password = user.password
        if (checkCredentials(username, password)) {
            return false
        } else {
            val query = "INSERT INTO USERS (username, password) VALUES (?, ?)"
            try {
                val connection = DatabaseFactory.getConnection()
                val statement = connection.prepareStatement(query)
                statement.setString(1, username)
                statement.setString(2, password)
                statement.executeQuery().use { resultSet ->
                    connection.close()
                    statement.close()
                    return resultSet.next()
                }
            } catch (e: SQLException) {

            }
            return false
        }
    }


    fun tryLogin(user: UserAuth):Boolean {
        val username = user.username
        val password = user.password
        return checkCredentials(username, password)
    }


    fun updateUITheme(user: User): Boolean {
        val username = user.username
        val theme = user.theme
        val query = "INSERT INTO USERS (username, theme) VALUES (?, ?)"
        try {
            val connection = DatabaseFactory.getConnection()
            val statement = connection.prepareStatement(query)
            statement.setString(1, username)
            statement.setBoolean(2, theme)
            statement.executeQuery().use { resultSet ->
                connection.close()
                statement.close()
                return resultSet.next()
            }
        } catch (e: SQLException) {

        }
        return false
    }
}
/*
    private fun resultSetToUser(resultSet: ResultSet): User {
        return User(
            userIdA = resultSet.getString("user_id"),
            userIdB = resultSet.getString("user_id_b"),
            username = resultSet.getString("title"),
            password = resultSet.getString("content"),
            createdAt = resultSet.getString("created_at")
        )
    }

    fun findById(id: UUID): User? {
        val connection = DatabaseFactory.getConnection()
        val statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")
        statement.setObject(1, id)
        val resultSet = statement.executeQuery()

        val user = if (resultSet.next()) resultSetToUser(resultSet) else null

        resultSet.close()
        statement.close()

        return user
    }

    fun findByUsername(username: String): User? {
        val connection = DatabaseFactory.getConnection()
        val statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?")
        statement.setString(1, username)
        val resultSet = statement.executeQuery()

        val user = if (resultSet.next()) resultSetToUser(resultSet) else null

        resultSet.close()
        statement.close()

        return user
    }

    fun createUser(user: User): User {
        val connection = DatabaseFactory.getConnection()
        val statement = connection.prepareStatement(
            "INSERT INTO users (id, username, password) VALUES (?, ?, ?)"
        )
        statement.setObject(1, user.id)
        statement.setString(2, user.username)
        statement.setString(3, user.password)
        statement.executeUpdate()
        statement.close()

        return user
    }
 */

