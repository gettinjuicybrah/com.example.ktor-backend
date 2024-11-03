package example.com.routing

import example.com.routing.response.pub.*
import example.com.service.*
import example.com.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import example.com.routing.request.pub.*
import example.com.service.RouteService
fun Route.publicRoutes() {
    route("/public") {
        post("/register") {
            val registerRequest = call.receive<RegisterRequest>()
            val response = RouteService.tryRegister(registerRequest)
            call.respond(response)
        }

        post("/login") {
            val loginRequest = call.receive<LoginRequest>()
            val response = RouteService.tryLogin(loginRequest)
            call.respond(response)
        }

    }
}