package example.com.models

data class AccessToken(
    val JWTToken: String
)

data class RefreshToken(
    val JWTToken: String
)