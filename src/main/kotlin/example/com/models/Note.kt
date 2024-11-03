package example.com.models

import java.util.*

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val version: String
)

