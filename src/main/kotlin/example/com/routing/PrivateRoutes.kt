package example.com.routing

import example.com.routing.request.priv.*
import example.com.service.RouteService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.privateRoutes() {

    route("/private") {

        post("/accessToken") {
            val request = call.receive<AccessTokenRequest>()
            val response = RouteService.getAccessToken(request)
            call.respond(response)
        }

        post("/uiTheme"){
            val request = call.receive<UpdateUIThemeRequest>()
            val response = RouteService.updateUITheme(request)
            call.respond(response)
        }

        post("/insertNote"){
            val request = call.receive<InsertNoteRequest>()
            val response = RouteService.insertNote(request)
            call.respond(response)
        }

        post("/updateNote"){
            val request = call.receive<UpdateNoteRequest>()
            val response = RouteService.updateNote(request)
            call.respond(response)
        }

        post("/deleteNote"){
            val request = call.receive<DeleteNoteRequest>()
            val response = RouteService.deleteNote(request)
            call.respond(response)
        }

        post("/insertFolder"){
            val request = call.receive<InsertFolderRequest>()
            val response = RouteService.insertFolder(request)
            call.respond(response)
        }

        post("/updateFolder"){
            val request = call.receive<UpdateFolderRequest>()
            val response = RouteService.updateFolder(request)
            call.respond(response)
        }

        post("/deleteFolder"){
            val request = call.receive<DeleteFolderRequest>()
            val response = RouteService.deleteFolder(request)
            call.respond(response)
        }

    }
}
/*
/*


private fun ApplicationCall.getUserId(jwtService: JwtService): UUID {
val principal = this.principal<JWTPrincipal>()
return jwtService.extractUserId(principal!!)
?: throw IllegalStateException("User ID not found in token")
}

 */