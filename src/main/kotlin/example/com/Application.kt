package example.com

import example.com.data.db.DatabaseFactory
import example.com.plugins.*
import example.com.routing.configureRouting
import example.com.service.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    JWTService.application = this
    DatabaseFactory.init(environment.config)

    configureSecurity()


    configureSerialization()

//configureRouting(jwtService, userService, noteService)
}
