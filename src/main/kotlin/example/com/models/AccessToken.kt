package example.com.models

import java.time.LocalDateTime

data class AccessToken(
    val id: String,
    val userIdA: String,
    val userIdB: String,
    val accessToken: String,
    val expiresAt: String
)