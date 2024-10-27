package example.com.routing.request.priv

import kotlinx.serialization.Serializable

@Serializable
data class GetAllNotesRequest(
    val accessToken: String
)