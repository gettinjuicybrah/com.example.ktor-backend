package example.com.routing.response.priv

import kotlinx.serialization.Serializable

@Serializable
data class GetAccessTokenResponse(
    val accessToken:String,
    val accessTokenExpiration:String
)
