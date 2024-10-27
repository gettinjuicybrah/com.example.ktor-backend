package example.com.models

data class Token(
    val id: String,
    val userIdA: String,
    val userIdB: String,
    val refreshToken: String,
    val refreshTokenExpiration: String,
    val accessToken: String,
    val accessTokenExpiration: String
)