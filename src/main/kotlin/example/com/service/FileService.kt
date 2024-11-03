package example.com.service

import example.com.models.*
import example.com.data.repository.*
object FileService {

    fun insertNote(note: Note, user: User):Boolean {
        return FileRepository.insertNote(note, user)
    }

    fun updateNote(note: Note, user: User):Boolean{
        return FileRepository.updateNote(note, user)
    }

    fun deleteNote(note: Note, user: User):Boolean{
        return FileRepository.deleteNote(note, user)
    }

    fun insertFolder(folder: Folder, user: User):Boolean {
        return FileRepository.insertFolder(folder, user)
    }

    fun updateFolder(folder: Folder, user: User):Boolean{
        return FileRepository.updateFolder(folder, user)
    }

    fun deleteFolder(folder: Folder, user: User):Boolean{
        return FileRepository.deleteFolder(folder, user)
    }
}