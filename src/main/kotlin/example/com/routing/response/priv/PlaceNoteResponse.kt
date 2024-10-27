package example.com.routing.response.priv

import kotlinx.serialization.Serializable

@Serializable
data class PlaceNoteResponse(
    val message: String
)
