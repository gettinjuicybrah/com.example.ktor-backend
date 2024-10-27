package example.com.routing.response.pub

import example.com.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class RegisterResponse(
    val message: String
)