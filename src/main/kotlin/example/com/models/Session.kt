package example.com.models

data class Session(
    val id: String,
    val userIdA: String,
    val userIdB: String,
    val device: String,
    val ipAddress: String,
    val token: String
)