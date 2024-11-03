package example.com.models

import java.util.*

data class User(
    //primary key
    val id: String,
    val username: String,
    val password: String,
    val theme: Boolean
)
data class UserAuth(
    val username: String,
    val password: String
)