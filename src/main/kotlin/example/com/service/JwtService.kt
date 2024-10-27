package example.com.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import example.com.data.repository.RefreshTokenRepository
import example.com.models.User
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
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
class JwtService(
    private val application: Application,
    private val userService: UserService,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    // SecureRandom instance for generating cryptographically strong random numbers
    private val secureRandom = SecureRandom()

    // Configuration properties loaded from the application.conf file
    private val secret = getConfigProperty("jwt.secret")
    private val issuer = getConfigProperty("jwt.issuer")
    private val audience = getConfigProperty("jwt.audience")
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
    fun createAccessToken(user: User): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", user.username)
            .withClaim("userId", user.id.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + 3_600_000)) // 1 hour expiration
            .sign(Algorithm.HMAC256(secret))
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
     * Validates an access token.
     *
     * @param credential The JWT credential to validate.
     * @return A JWTPrincipal if the token is valid, null otherwise.
     */
    fun validateAccessToken(credential: JWTCredential): JWTPrincipal? {
        val username = credential.payload.getClaim("username").asString()
        val user = userService.findByUsername(username)
        return if (user != null && credential.payload.audience.contains(audience)) {
            JWTPrincipal(credential.payload)
        } else null
    }

    /**
     * Creates a refresh token for a given user ID.
     *
     * @param userId The ID of the user for whom the refresh token is being created.
     * @return A string representing the refresh token.
     */
    fun createRefreshToken(userId: UUID): String {
        val token = generateRandomToken()
        val expiresAt = LocalDateTime.now().plusDays(30) // 30 days expiration
        refreshTokenRepository.saveRefreshToken(userId, token, expiresAt)
        return token
    }

    /**
     * Refreshes an access token using a refresh token.
     *
     * @param refreshToken The refresh token to use.
     * @return A new access token if successful, null otherwise.
     */
    fun refreshAccessToken(refreshToken: String): String? {

        val storedToken = refreshTokenRepository.findByToken(refreshToken)
        if (storedToken != null && storedToken.expiresAt.isAfter(LocalDateTime.now())) {
            val user = userService.findById(storedToken.userId)
            if (user != null) {
                return createAccessToken(user)
            }
        }
        return null
    }

    /**
     * Revokes a refresh token.
     *
     * @param refreshToken The refresh token to revoke.
     */
    fun revokeRefreshToken(refreshToken: String) {
        refreshTokenRepository.deleteToken(refreshToken)
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