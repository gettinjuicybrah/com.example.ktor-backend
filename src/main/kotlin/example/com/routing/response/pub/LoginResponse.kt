package example.com.routing.response.pub

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val accessTokenExpiration: String, // Expiration time in milliseconds since epoch
    val refreshToken: String,
    val refreshTokenExpiration: String // Expiration time in milliseconds since epoch
)