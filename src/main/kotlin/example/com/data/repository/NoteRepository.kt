package example.com.data.repository

import example.com.data.db.DatabaseFactory
import example.com.models.Note
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class NoteRepository {

    private fun resultSetToNote(resultSet: ResultSet): Note {
        return Note(
            noteIdA = resultSet.getString("idA"),
            noteIdB = resultSet.getString("idB"),
            userIdA = resultSet.getString("userIdA"),
            userIdB = resultSet.getString("userIdB"),
            title = resultSet.getString("title"),
            content = resultSet.getString("content"),
            createdAt = resultSet.getString("created_at"),
            updatedAt = resultSet.getString("updated_at")
        )
    }

    fun getAllNotesByUserIds(userIdA: String, userIdB: String): List<Note> {
        val notes = mutableListOf<Note>()
        val connection = DatabaseFactory.getConnection()
        val statement = connection.prepareStatement("SELECT * FROM notes WHERE userIdA = ? AND userIdB = ?")
        statement.setString(1, userIdA)
        statement.setString(2, userIdB)
        val resultSet = statement.executeQuery()

        while(resultSet.next()){
            notes.add(resultSetToNote(resultSet))
        }

        resultSet.close()
        statement.close()

        return notes;
    }


    fun insertNote(note: Note){
        val connection = DatabaseFactory.getConnection()
        val statement = connection.prepareStatement(
            "INSERT INTO notes (noteIdA, noteIdB, userIdA, userIdB, title, content, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
        statement.setString(1, note.noteIdA)
        statement.setString(2, note.noteIdB)
        statement.setString(3, note.userIdA)
        statement.setString(4, note.userIdB)
        statement.setString(5, note.title)
        statement.setString(6, note.content)
        statement.setString(7, note.createdAt)
        statement.setString(8, note.updatedAt)
        statement.executeUpdate()
        statement.close()
    }

    fun updateNote(note: Note){
        val connection = DatabaseFactory.getConnection()
        val statement = connection.prepareStatement("UPDATE notes SET title = ?, content = ?, updated_at = ? " +
                "WHERE noteIdA = ? AND noteIdB = ? AND userIdA = ? AND userIdB = ?")
        statement.setString(1, note.title)
        statement.setString(2, note.content)
        statement.setString(3, note.updatedAt)
        statement.setString(4, note.noteIdA)
        statement.setString(5, note.noteIdB)
        statement.setString(6, note.userIdA)
        statement.setString(7, note.userIdB)
        statement.executeUpdate()
        statement.close()
    }

    fun deleteNote(note: Note){
        val connection = DatabaseFactory.getConnection()
        val statement = connection.prepareStatement("DELETE FROM notes WHERE noteIdA = ? AND noteIdB = ?" +
                "AND userIdA = ? AND userIdB = ?")
        statement.setString(1, note.noteIdA)
        statement.setString(2, note.noteIdB)
        statement.setString(3, note.userIdA)
        statement.setString(4, note.userIdB)
        statement.execute()
        statement.close()

    }
}