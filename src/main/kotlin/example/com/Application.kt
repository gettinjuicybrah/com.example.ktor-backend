package example.com

import example.com.data.db.DatabaseFactory
import example.com.data.repository.NoteRepository
import example.com.data.repository.RefreshTokenRepository
import example.com.data.repository.UserRepository
import example.com.plugins.*
import example.com.routing.configureRouting
import example.com.service.JwtService
import example.com.service.NoteService
import example.com.service.UserService
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    DatabaseFactory.init(environment.config)

    val userRepository = UserRepository()
    val userService = UserService(userRepository)

    val refreshTokenRepository = RefreshTokenRepository()

    val jwtService = JwtService(this, userService, refreshTokenRepository)
    configureSecurity(jwtService)

    val noteRepository = NoteRepository()
    val noteService = NoteService(noteRepository)

    configureSerialization()
    configureRouting(jwtService, userService, noteService)
}
