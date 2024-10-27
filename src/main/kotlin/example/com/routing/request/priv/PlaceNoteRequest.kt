package example.com.routing.request.priv

import kotlinx.serialization.Serializable

@Serializable
data class PlaceNoteRequest(
    val noteIdA: String,
    val noteIdB: String,
    val title: String,
    val content: String,
    val lastEditDate: String,
    val creationDate: String,
    val accessToken: String
)
