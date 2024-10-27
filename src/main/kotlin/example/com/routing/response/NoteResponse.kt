package example.com.routing.response

import example.com.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class NoteResponse(
    val id: Int,
    val title: String,
    val content: String,
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID
)