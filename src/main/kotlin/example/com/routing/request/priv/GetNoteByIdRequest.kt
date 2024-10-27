package example.com.routing.request.priv

import kotlinx.serialization.Serializable

@Serializable
data class GetNoteByIdRequest(
    val noteIdA: String,
    val noteIdB: String,
    val accessToken: String
)