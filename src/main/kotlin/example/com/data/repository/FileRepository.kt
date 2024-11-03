package example.com.data.repository
import example.com.data.db.DatabaseFactory
import example.com.models.Folder
import example.com.models.Note
import example.com.models.User
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*
object FileRepository {


    fun insertNote(note: Note, user: User):Boolean {
        try {
            val connection = DatabaseFactory.getConnection()
            val statement = connection.prepareStatement(
                "INSERT INTO NOTES (userID, title, content, createdAt, updatedAt, version) " +
                        "VALUES (?, ?, ?, ?, ?, ?)"
            )
            statement.setString(1, user.id)
            statement.setString(2, note.title)
            statement.setString(3, note.content)
            statement.setString(4, note.createdAt)
            statement.setString(5, note.updatedAt)
            statement.setString(6, note.version)
            statement.executeUpdate()
            statement.close()
            return true
        }catch (ex: SQLException) {
            return false
        }
    }

    fun updateNote(note: Note, user: User):Boolean{
        try {

            val connection = DatabaseFactory.getConnection()
            val statement = connection
                .prepareStatement(
                    "UPDATE NOTES SET title = ?, content = ?, createdAt = ? ," +
                            "updatedAt = ? , version = ? WHERE userID = ?"
                )
            statement.setString(1, note.title)
            statement.setString(2, note.content)
            statement.setString(3, note.createdAt)
            statement.setString(4, note.updatedAt)
            statement.setString(5, note.version)
            statement.setString(6, user.id)
            statement.executeUpdate()
            statement.close()
            return true
        }catch (e: SQLException){
            return false
        }
    }

    fun deleteNote(note: Note, user: User):Boolean{
        try {


            val connection = DatabaseFactory.getConnection()
            val statement = connection.prepareStatement("DELETE FROM NOTES WHERE id = ? AND userID = ?")
            statement.setString(1, note.id)
            statement.setString(2, user.id)
            statement.execute()
            statement.close()
            return true
        }catch (e: SQLException){
            return false
        }
    }

    fun insertFolder(folder: Folder, user: User):Boolean {
        try {
            val connection = DatabaseFactory.getConnection()
            val statement = connection.prepareStatement(
                "INSERT INTO FOLDERS (id, userID, parentID, title, isToggled) " +
                        "VALUES (?, ?, ?, ?, ?)"
            )
            statement.setString(1, folder.id)
            statement.setString(2, user.id)
            statement.setString(3, folder.parentID)
            statement.setString(4, folder.title)
            statement.setBoolean(5, folder.isToggled)
            statement.executeUpdate()
            statement.close()
            return true
        }catch (ex: SQLException) {
            return false
        }
    }

    fun updateFolder(folder: Folder, user: User):Boolean{
        try {
            val connection = DatabaseFactory.getConnection()
            val statement = connection
                .prepareStatement(
                    "UPDATE FOLDERS SET title = ?, parentID = ?, isToggled = ? ," +
                            "updatedAt = ? , version = ? WHERE id = ? AND userID = ?"
                )
            statement.setString(1, folder.title)
            statement.setString(2, folder.parentID)
            statement.setBoolean(3, folder.isToggled)
            statement.setString(4, folder.id)
            statement.setString(5, user.id)
            statement.executeUpdate()
            statement.close()
            return true
        }catch(e: SQLException){
            return false
        }
    }

    fun deleteFolder(folder: Folder, user: User):Boolean{
        try {
            val connection = DatabaseFactory.getConnection()
            val statement = connection.prepareStatement("DELETE FROM FOLDERS WHERE id = ? AND userID = ?")
            statement.setString(1, folder.id)
            statement.setString(2, user.id)
            statement.execute()
            statement.close()
            return true
        } catch (e: SQLException) {
         return false
        }
    }

}