package example.com.service

import example.com.data.repository.UserRepository
import example.com.models.*
import example.com.routing.response.priv.*
import example.com.routing.response.pub.*
import example.com.routing.request.priv.*
import example.com.routing.request.pub.*

object RouteService {

    fun tryLogin(loginRequest: LoginRequest): LoginResponse {
        val user = UserAuth(loginRequest.username, loginRequest.password)
        return if (UserService.tryRegister(user)){
            LoginResponse.Success(
                JWTService.createAccessToken(),
                JWTService.createRefreshToken(),
                UserRepository.getUserID(user.username)
            )
        } else{
            LoginResponse.Fail(false)
        }
    }

    fun tryRegister(registerRequest: RegisterRequest): RegisterResponse {
        val user = UserAuth(registerRequest.username, registerRequest.password)
        return RegisterResponse(UserService.tryRegister(user))
    }

    fun updateUITheme(updateUIThemeRequest: UpdateUIThemeRequest): UpdateUIThemeResponse {
        return UpdateUIThemeResponse(UserService.updateUITheme(updateUIThemeRequest.user))
    }

    fun getAccessToken(accessTokenRequest: AccessTokenRequest): AccessTokenResponse {
        //NOT EVEN NEEDED. THE REFRESH TOKEN WILL BE REQUIRED TO EVEN ACCESS THE PRIVATE ROUTE.
        //val refreshToken = accessTokenRequest.refreshToken
        return AccessTokenResponse(JWTService.getAccessToken())
    }

    fun insertNote(insertNoteRequest: InsertNoteRequest): InsertNoteResponse {
        val note = insertNoteRequest.note
        val user = insertNoteRequest.user
        return InsertNoteResponse(FileService.insertNote(note, user))
    }
    fun updateNote(updateNoteRequest: UpdateNoteRequest): UpdateNoteResponse {
        val note = updateNoteRequest.note
        val user = updateNoteRequest.user
        return UpdateNoteResponse(FileService.updateNote(note, user))
    }
    fun deleteNote(deleteNoteRequest: DeleteNoteRequest): DeleteNoteResponse {
        val note = deleteNoteRequest.note
        val user = deleteNoteRequest.user
        return DeleteNoteResponse(FileService.deleteNote(note, user))
    }

    fun insertFolder(insertFolderRequest: InsertFolderRequest): InsertFolderResponse {
        val folder = insertFolderRequest.note
        val user = insertFolderRequest.user
        return InsertFolderResponse(FileService.insertFolder(folder, user))
    }
    fun updateFolder(updateFolderRequest: UpdateFolderRequest): UpdateFolderResponse {
        val folder = updateFolderRequest.note
        val user = updateFolderRequest.user
        return UpdateFolderResponse(FileService.updateFolder(folder, user))
    }
    fun deleteFolder(deleteFolderRequest: DeleteFolderRequest): DeleteFolderResponse {
        val folder = deleteFolderRequest.note
        val user = deleteFolderRequest.user
        return DeleteFolderResponse(FileService.deleteFolder(folder, user))
    }

}