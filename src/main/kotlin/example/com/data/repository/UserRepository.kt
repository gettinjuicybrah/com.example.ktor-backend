package example.com.data.repository

import example.com.data.db.DatabaseFactory
import example.com.models.User
import java.sql.ResultSet
import java.util.*

class UserRepository {

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

}