package example.com.service

import example.com.data.repository.UserRepository
import example.com.models.User
import java.util.*

class UserService(
    private val userRepository: UserRepository,
){
    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    fun verifyPassword(inputPassword: String, storedPassword: String): Boolean {
        // In a real application, you'd use a secure hashing algorithm here
        // This is a simplified example and is NOT secure for production use
        return inputPassword == storedPassword
    }
    fun findById(id: UUID): User? {
        return userRepository.findById(id)
    }
    fun createUser(username: String, password: String): User {
        // Check if the username already exists
        if (findByUsername(username) != null) {
            throw IllegalArgumentException("Username already exists")
        }
        // In a real application, you should hash the password before storing it
        val newUser = User(UUID.randomUUID().toString(), UUID.randomUUID().toString(),username, password, )
        return userRepository.createUser(newUser)
    }
}