package example.com.models


data class RefreshToken(
    val id: String,
    val userIdA: String,
    val userIdB: String,
    val refreshToken: String,
    val expiresAt: String
)