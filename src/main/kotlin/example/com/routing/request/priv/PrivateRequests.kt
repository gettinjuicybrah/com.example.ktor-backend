package example.com.routing.request.priv

import example.com.models.Note
import example.com.models.RefreshToken
import example.com.models.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenRequest(
    @Contextual
    /*doesn't make any sense because the refresh token will be in the authentication header to even get
    access to the SPECIFICALLY PRIVATE ROUTE
    */
    val refreshToken: RefreshToken
)
@Serializable
data class UpdateUIThemeRequest(
    @Contextual
    val user: User
)
@Serializable
data class InsertNoteRequest(
    @Contextual
    val user: User,
    @Contextual
    val note: Note
)
@Serializable
data class UpdateNoteRequest(
    @Contextual
    val user: User,
    @Contextual
    val note: Note
)
@Serializable
data class DeleteNoteRequest(
    @Contextual
    val user: User,
    @Contextual
    val note: Note
)

@Serializable
data class InsertFolderRequest(
    @Contextual
    val user: User,
    @Contextual
    val note: Folder
)
@Serializable
data class UpdateFolderRequest(
    @Contextual
    val user: User,
    @Contextual
    val note: Folder
)
@Serializable
data class DeleteFolderRequest(
    @Contextual
    val user: User,
    @Contextual
    val note: Folder
)