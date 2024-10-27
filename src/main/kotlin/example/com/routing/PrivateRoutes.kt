package example.com.routing

import example.com.routing.request.priv.GetAllNotesRequest
import example.com.routing.request.priv.GetNoteByIdRequest
import example.com.routing.request.priv.PlaceNoteRequest
import example.com.routing.request.priv.GetAccessTokenRequest
import example.com.routing.response.priv.RefreshAccessTokenResponse
import example.com.service.JwtService
import example.com.service.NoteService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.privateRoutes(jwtService: JwtService, noteService: NoteService) {

    route("/private") {
        /*
        get {
            val userId = call.getUserId(jwtService)
            val notes = noteService.getAllNotesForUser(userId)
            val response = notes.map { NoteResponse(it.id, it.title, it.content, it.userId) }
            call.respond(response)
        }
         */
        /*
        Refresh access token request using a valid refresh token.
         */
        post("/RequestAccessToken") {
            val request = call.receive<GetAccessTokenRequest>()
            val newAccessToken = jwtService.refreshAccessToken(request.refreshToken)
            if (newAccessToken != null) {
                val response = RefreshAccessTokenResponse(newAccessToken)
                call.respond(response)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid refresh token")
            }
        }

        post("/PlaceNote") {
            val request = call.receive<PlaceNoteRequest>()
        }

        post("/GetAllNotes") {
            val request = call.receive<GetAllNotesRequest>()
        }

        post("GetNoteById") {
            val request = call.receive<GetNoteByIdRequest>()
        }
    }
}
        /*
        /*
        Get note by id
         */
        get("/{id}") {
            val noteId = call.parameters["id"]?.toIntOrNull()
            if (noteId != null) {
                val note = noteService.getNoteById(noteId)
                if (note != null) {
                    val response = NoteResponse(note.id, note.title, note.content, note.userId)
                    call.respond(response)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Note not found")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid note ID")
            }
        }

        /*
        Insert note
         */
        post {
            val userId = call.getUserId(jwtService)
            val noteRequest = call.receive<NoteRequest>()
            val note = Note(0, noteRequest.title, noteRequest.content, userId)
            val createdNote = noteService.createNote(note)
            val response = NoteResponse(createdNote.id, createdNote.title, createdNote.content, createdNote.userId)
            call.respond(HttpStatusCode.Created, response)
        }

        put("/{id}") {
            val noteId = call.parameters["id"]?.toIntOrNull()
            if (noteId != null) {
                val noteRequest = call.receive<NoteRequest>()
                val existingNote = noteService.getNoteById(noteId)
                if (existingNote != null) {
                    val updatedNote = existingNote.copy(title = noteRequest.title, content = noteRequest.content)
                    val result = noteService.updateNote(updatedNote)
                    val response = NoteResponse(result.id, result.title, result.content, result.userId)
                    call.respond(response)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Note not found")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid note ID")
            }
        }

        /*
        Delte note by id
         */
        delete("/{id}") {
            val noteId = call.parameters["id"]?.toIntOrNull()
            if (noteId != null) {
                val success = noteService.deleteNote(Note(noteId, "", "", UUID.randomUUID()))
                if (success) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Note not found")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid note ID")
            }
        }
    }
}

private fun ApplicationCall.getUserId(jwtService: JwtService): UUID {
    val principal = this.principal<JWTPrincipal>()
    return jwtService.extractUserId(principal!!)
        ?: throw IllegalStateException("User ID not found in token")
}

         */