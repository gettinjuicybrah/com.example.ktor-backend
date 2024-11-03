package example.com.routing
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

/*
REQUEST TYPES:
    POST:
        USED TO SEND DATA TO A SERVER TO CREATE OR UPDATE
        A RESOURCE. THE DATA SENT TO THE SERVER WITH THE POST REQUEST
        IS INCLUDED IN THE BODY OF THE REQUEST.

        ONE OF THE MOST COMMON TYPES OF HTTP METHODS AND
        ARE OFTEN USED IN FORM SUBMISSIONS, UPLOADING FILES, AND
        MAKING CHANGES TO SERVER-SIDE DATA.

    GET:
        USED TO REQUEST DATA FROM A SPECIFIED RESOURCE.
        WHEN DEFINING ROUTES IN KTOR, CAN USE THE
        'get' FUNCTION TO HANDLE GET REQUESTS.

    PUT:
        USED TO UPLOAD A RESOURCE TO THE SERVER. PRIMARILY
        USED TO CREATE OR REPLACE A RESOURCE AT A SPECIFIED URL
        WITH THE REQUEST PAYLOAD.

        PAYLOAD:
            THE PART OF THE REQUEST THAT CONTAINS
            THE ACTUALLY DATA BEING TRANSMITTED.
 */
/**
 * Configures the routing for the application.
 * This function sets up all the endpoints for user authentication and note management.
 *
 * @param jwtService Service for JWT operations
 * @param userService Service for user-related operations
 * @param noteService Service for note-related operations
 */
fun Application.configureRouting(
) {
    routing {
        // Public routes (no authentication required)
        publicRoutes()

        // Protected routes (authentication required)
        authenticate("jwt") {
            privateRoutes()
        }
    }
}
