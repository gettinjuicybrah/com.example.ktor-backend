package example.com.routing

import example.com.routing.request.pub.LoginRequest
import example.com.routing.request.pub.RegisterRequest
import example.com.routing.response.pub.LoginResponse
import example.com.routing.response.pub.RegisterResponse
import example.com.service.JwtService
import example.com.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.publicRoutes(jwtService: JwtService, userService: UserService) {
    route("/public") {
        post("/register") {
            val registerRequest = call.receive<RegisterRequest>()
            val user = userService.createUser(registerRequest.username, registerRequest.password)
            val response = RegisterResponse("")
            call.respond(HttpStatusCode.Created, response)
        }

        post("/login") {
            val loginRequest = call.receive<LoginRequest>()
            val user = userService.findByUsername(loginRequest.username)
            if (user != null && userService.verifyPassword(loginRequest.password, user.password)) {
                val accessToken = jwtService.createAccessToken(user)
                val refreshToken = jwtService.createRefreshToken(user.id)
                val accessTokenExpiration = jwtService.calculateAccessTokenExp().toString()
                val refreshTokenExpiration = jwtService.calculateRefreshTokenExp().toString()
                val response = LoginResponse(accessToken, accessTokenExpiration,
                    refreshToken, refreshTokenExpiration)
                call.respond(response)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
            }
        }

    }
}