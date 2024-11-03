package example.com.routing.response.pub

import example.com.models.AccessToken
import example.com.models.RefreshToken
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val boolResult: Boolean
)
sealed class LoginResponse {
    @Serializable
    data class Success(
        @Contextual
        val accessToken: AccessToken,
        @Contextual
        val refreshToken: RefreshToken,
        val userId: String
    ) : LoginResponse()

    data class Fail(
        val fail: Boolean
    ) : LoginResponse()
}
