package example.com.routing.request.priv

import kotlinx.serialization.Serializable

@Serializable
data class GetAccessTokenRequest(
    val refreshToken: String
)