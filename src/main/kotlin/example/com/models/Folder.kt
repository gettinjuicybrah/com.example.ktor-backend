package example.com.models

data class Folder(
    val id: String,
    val title: String,
    val parentID: String,
    val isToggled: Boolean
)
