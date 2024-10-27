package example.com.routing.response.priv

import kotlinx.serialization.Serializable

@Serializable
data class GetAllNotesResponse(
    val list: List<Note>
)
@Serializable
data class Note(
    val noteIdA: String,
    val noteIdB: String,
    val title: String,
    val content: String,
    val lastEditDate: String,
    val creationDate: String
)
