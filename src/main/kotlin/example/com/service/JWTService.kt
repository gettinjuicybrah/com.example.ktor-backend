package example.com.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import example.com.main
import example.com.models.*
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import java.security.SecureRandom
import java.time.LocalDateTime
import java.util.*

/**
 * JwtService is responsible for managing JSON Web Tokens (JWTs) for authentication and authorization.
 * It handles both access tokens and refresh tokens.
 *
 * @property application The Ktor Application instance, used for configuration.
 * @property userService Service for user-related operations.
 * @property refreshTokenRepository Repository for storing and retrieving refresh tokens.
 */
object JWTService {
    // SecureRandom instance for generating cryptographically strong random numbers
    private val secureRandom = SecureRandom()
    lateinit var application: Application
    // Configuration properties loaded from the application.conf file
    val secret = getConfigProperty("jwt.secret")
    val issuer = getConfigProperty("jwt.issuer")
    val audience = getConfigProperty("jwt.audience")
    val realm = getConfigProperty("jwt.realm")

    /**
     * JWT Verifier used to validate the authenticity of incoming JWTs.
     * It checks if the token was signed with the correct secret, and has the correct issuer and audience.
     * USED TO VERIFY THE TOKENS SENT TO THE REST API
     */
    val jwtVerifier: JWTVerifier = JWT
        .require(Algorithm.HMAC256(secret))
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    /**
     * Creates an access token for a given user.
     *
     * @param user The user for whom the token is being created.
     * @return A JWT string representing the access token.
     */
    fun createAccessToken(): AccessToken {
        return AccessToken(JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + 3_600_000)) // 1 hour expiration
            .sign(Algorithm.HMAC256(secret)))
    }
    fun createRefreshToken(): RefreshToken {
        return RefreshToken(JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + 86_400_000)) // 24 hour expiration
            .sign(Algorithm.HMAC256(secret)))
    }
    fun calculateAccessTokenExp():Long{
        val currentTime = System.currentTimeMillis()
        val accessTokenExpiration = currentTime + 60 * 60 * 1000 // 1 hour in milliseconds
        return accessTokenExpiration
    }
    fun calculateRefreshTokenExp():Long{
        val currentTime = System.currentTimeMillis()
        val refreshTokenExpiration = currentTime + 7 * 24 * 60 * 60 * 1000 // 1 week in milliseconds
        return refreshTokenExpiration
    }


    /**
     * Creates a refresh token for a given user ID.
     *
     * @return A string representing the refresh token.
     */
    /*
    fun createRefreshToken(): RefreshToken {
        val token = generateRandomToken()
        val expiry = LocalDateTime.now().plusDays(30).toString() // 30 days expiration
        return RefreshToken(token, expiry)
    }

     */

    /**
     * Refreshes an access token using a refresh token.
     *
     * @param refreshToken The refresh token to use.
     * @return A new access token if successful, null otherwise.
     */
    fun getAccessToken(): AccessToken {
        return createAccessToken()
    }


    /**
     * Extracts the user ID from a JWT principal.
     *
     * @param principal The JWT principal.
     * @return The user ID as a UUID, or null if it couldn't be extracted.
     */
    fun extractUserId(principal: JWTPrincipal): UUID? =
        principal.payload.getClaim("userId").asString()?.let { UUID.fromString(it) }

    /**
     * Generates a cryptographically secure random token.
     *
     * @return A random token as a Base64 encoded string.
     */
    private fun generateRandomToken(): String {
        val randomBytes = ByteArray(32) // 256 bits
        secureRandom.nextBytes(randomBytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes)
    }

    /**
     * Retrieves a configuration property from the application.conf file.
     *
     * @param path The path to the property in the configuration file.
     * @return The value of the property as a string.
     */
    private fun getConfigProperty(path: String): String =
        application.environment.config.property(path).getString()
}