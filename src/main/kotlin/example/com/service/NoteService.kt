package example.com.service

import example.com.data.repository.NoteRepository
import example.com.models.Note
import java.util.*

class NoteService(
    private val noteRepository: NoteRepository
) {
    fun getAllNotesByUserIds(userIdA: String, userIdB: String): List<Note> =
        noteRepository.getAllNotesByUserIds(userIdA, userIdB)

    fun insertNote(note: Note) {
        noteRepository.insertNote(note)
    }

    fun updateNote(note: Note){
        noteRepository.updateNote(note)
    }

    fun deleteNote(note: Note){
        noteRepository.deleteNote(note)
    }
}