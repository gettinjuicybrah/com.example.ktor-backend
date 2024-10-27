package example.com.routing.response.priv

import kotlinx.serialization.Serializable

@Serializable
data class GetNoteByIdResponse(
    val title: String,
    val content: String,
    val lastEditDate: String,
    val creationDate: String
)