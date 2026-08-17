package com.surgex.app.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

enum class UserRole { RIDER, DRIVER }

data class UserProfile(
    val uid: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val activeMode: UserRole,
    val accountStatus: String
)

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
                "activeMode" to role.name,
                "accountStatus" to "APPROVED",
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
            AuthResult.Error(e.message ?: "Login failed. Check your email and password.")
        }
    }

    suspend fun getCurrentUserProfile(): UserProfile? {
        val uid = auth.currentUser?.uid ?: return null
        return try {
            val doc = db.collection("users").document(uid).get().await()
            if (!doc.exists()) return null
            val roleStr = doc.getString("role") ?: "RIDER"
            val activeModeStr = doc.getString("activeMode") ?: roleStr
            val accountStatus = doc.getString("accountStatus") ?: "APPROVED"
            UserProfile(
                uid = uid,
                name = doc.getString("name") ?: "",
                email = doc.getString("email") ?: "",
                role = if (roleStr == "DRIVER") UserRole.DRIVER else UserRole.RIDER,
                activeMode = if (activeModeStr == "DRIVER") UserRole.DRIVER else UserRole.RIDER,
                accountStatus = accountStatus
            )
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveActiveMode(role: UserRole): Boolean {
        val uid = auth.currentUser?.uid ?: return false
        return try {
            db.collection("users").document(uid)
                .update("activeMode", role.name).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun logout() {
        auth.signOut()
    }
}
