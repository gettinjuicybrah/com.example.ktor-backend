package example.com.data.repository

import example.com.data.db.DatabaseFactory
import example.com.models.AccessToken
import example.com.models.Token
import java.sql.ResultSet
import java.time.LocalDateTime
import java.util.*

/*
Responsible for storage and retrieval of tokens.
 */
class TokenRepository {

    private fun resultSetToToken(resultSet: ResultSet): Token{
        return Token(
            id = resultSet.getString("id"),
            userIdA = resultSet.getString("userIdA"),
            userIdB = resultSet.getString("userIdB"),
            refreshToken = resultSet.getString("refreshToken"),
            refreshTokenExpiration = resultSet.getString("refreshTokenExpiration"),
            accessToken = resultSet.getString("accessToken"),
            accessTokenExpiration = resultSet.getString("accessTokenExpiration")
        )

    }
    private fun resultToAcessToken(resultSet: ResultSet): AccessToken {
        return AccessToken(
            id = resultSet.getString("id"),
            userIdA = resultSet.getString("userIdA"),
            userIdB = resultSet.getString("userIdB"),
            accessToken = resultSet.getString("accessToken"),
            expiresAt = resultSet.getString("expiresAt")
        )
    }


    /**
     * Finds the user ID associated with a given refresh token.
     *
     * @param token The refresh token string
     * @return The associated user ID as UUID, or null if not found
     */
    fun findUserIdByToken(token: String): UUID? {
        val connection = DatabaseFactory.getConnection()
        val statement = connection.prepareStatement("SELECT user_id FROM refresh_tokens WHERE token = ?")
        statement.setString(1, token)
        val resultSet = statement.executeQuery()

        val userId = if (resultSet.next()) {
            UUID.fromString(resultSet.getString("user_id"))
        } else null

        resultSet.close()
        statement.close()

        return userId
    }

    fun findByToken(token: String): RefreshToken? {
        val connection = DatabaseFactory.getConnection()
        val statement = connection.prepareStatement("SELECT * FROM refresh_tokens WHERE token = ?")
        statement.setString(1, token)
        val resultSet = statement.executeQuery()

        val refreshToken = if (resultSet.next()) resultSetToRefreshToken(resultSet) else null

        resultSet.close()
        statement.close()

        return refreshToken
    }

    fun saveRefreshToken(userId: UUID, token: String, expiresAt: LocalDateTime): RefreshToken {
        val connection = DatabaseFactory.getConnection()
        val statement = connection.prepareStatement(
            "INSERT INTO refresh_tokens (id, user_id, token, expires_at) VALUES (?, ?, ?, ?)"
        )
        val id = UUID.randomUUID()
        statement.setObject(1, id)
        statement.setObject(2, userId)
        statement.setString(3, token)
        statement.setTimestamp(4, java.sql.Timestamp.valueOf(expiresAt))
        statement.executeUpdate()
        statement.close()

        return RefreshToken(id, userId, token, expiresAt)
    }

    fun deleteToken(token: String) {
        val connection = DatabaseFactory.getConnection()
        val statement = connection.prepareStatement("DELETE FROM refresh_tokens WHERE token = ?")
        statement.setString(1, token)
        statement.executeUpdate()
        statement.close()
    }

    fun deleteExpiredTokens() {
        val connection = DatabaseFactory.getConnection()
        val statement = connection.prepareStatement("DELETE FROM refresh_tokens WHERE expires_at < ?")
        statement.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()))
        statement.executeUpdate()
        statement.close()
    }
}