package example.com.models

import java.util.*

data class Note(
    val noteIdA: String,
    val noteIdB: String,
    val userIdA: String,
    val userIdB: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String
)

