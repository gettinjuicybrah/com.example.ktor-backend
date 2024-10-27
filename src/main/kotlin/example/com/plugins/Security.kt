package example.com.plugins


import example.com.service.JwtService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

/*
this is a Ktor extension function for the
Application class. It's used to configure the security settings for your Ktor application.

e
 */
fun Application.configureSecurity(
    jwtService: JwtService
) {
    aia
    //Here, we are using ktor's authentication feature
    authentication{
        //Here, we are specifically configuring JWT (JSON Web Token) authentication
        jwt {
            /*
            This sets the "realm" for the authentication. The realm is the
            string that defines the protected area.
             */
            realm = jwtService.realm

            /*
            This sets the JWT verifier.
            The verifier is used to check if an incoming JWT is
            valid (correctly signed, not expired, etc.).
             */
            verifier(jwtService.jwtVerifier)
            /*
            This is the most important part. For each incoming
            request with a JWT, this function will be called. It takes the
            JWT credential and passes it to jwtService.validateAccessToken().
            This method should return a JWTPrincipal object if the token is valid,
            or null if it's not.

            JWTCredential is a class provided by Ktor that represents the raw, decoded
            JWT token.
            It contains:
            payload: The decoded JWT payload, which includes all the claims in the token.
            issuer: The issuer of the token (if present in the token).
            audience: The intended audience of the token (if present in the token).

            JWTCredential is essentially the raw data from the JWT, before any
            application-specific validation has been done. It's what your application
            receives to validate.

            JWTPrincipal is another class provided by Ktor. It represents an authenticated principal, i.e.,
            a successfully validated JWT token. It's a wrapper around the JWT payload that your application
            can use to access claims and other information from the token.
            The key difference is that a JWTPrincipal represents a token that has been validated and accepted
            by your application's logic.
            How they're used:

            When a request comes in with a JWT, Ktor creates a JWTCredential from the token.
            This JWTCredential is passed to your validate function (the one you define in configureSecurity).
            Your validate function is responsible for checking if this credential is valid for your
            application. This usually involves checking if the user exists, if the token hasn't been
            revoked, etc.
            If the credential is valid, you create and return a JWTPrincipal from it. If not, you
            return null.
            If a JWTPrincipal is returned, Ktor considers the request authenticated and allows it
            to proceed. The JWTPrincipal is then available in your route handlers for further use
            (like getting the user's ID).
             */
            validate{
                    credential -> jwtService.validateAccessToken(credential)
            }
        }
    }
}