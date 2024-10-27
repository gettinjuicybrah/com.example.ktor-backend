package example.com.models

import java.util.*

data class User(
    //primary key
    val userIdA: String,
    val userIdB: String,
    val username: String,
    val password: String,
    val createdAt: String
)
