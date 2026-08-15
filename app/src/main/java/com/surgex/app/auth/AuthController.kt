package com.surgex.app.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

enum class UserRole { RIDER, DRIVER }

sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthController {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    val currentUser: FirebaseUser? get() = auth.currentUser
    val isLoggedIn: Boolean get() = auth.currentUser != null

    suspend fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        role: UserRole
    ): AuthResult {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: return AuthResult.Error("Registration failed.")
            val userData = hashMapOf(
                "name" to name,
                "email" to email,
                "phone" to phone,
                "role" to role.name,
                "createdAt" to System.currentTimeMillis()
            )
            db.collection("users").document(uid).set(userData).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Registration failed.")
        }
    }

    suspend fun login(email: String, password: String): AuthResult {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Login failed.")
        }
    }

    fun logout() {
        auth.signOut()
    }
}
