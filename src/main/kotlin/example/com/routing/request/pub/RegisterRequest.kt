package example.com.routing.request.pub

import kotlinx.serialization.Serializable
/*
When a client sends data (e.g., in a POST request), Ktor can automatically deserialize the incoming JSON into an instance of RegisterRequest.
Similarly, when sending responses, Ktor can serialize instances of your data classes into JSON automatically.
 */
@Serializable
data class RegisterRequest (
    val username: String,
    val password: String
    )