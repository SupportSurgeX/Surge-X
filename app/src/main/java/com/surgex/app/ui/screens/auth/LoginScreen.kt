package com.surgex.app.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

enum class UserRole {
    RIDER,
    DRIVER
}

sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthController {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    val isLoggedIn: Boolean
        get() = auth.currentUser != null

    suspend fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        role: UserRole
    ): AuthResult {
        return try {
            val result = auth
                .createUserWithEmailAndPassword(email, password)
                .await()

            val uid = result.user?.uid
                ?: return AuthResult.Error("Registration failed.")

            val userData = hashMapOf(
                "name" to name,
                "email" to email,
                "phone" to phone,
                "role" to role.name,
                "createdAt" to System.currentTimeMillis()
            )

            db.collection("users")
                .document(uid)
                .set(userData)
                .await()

            AuthResult.Success

        } catch (e: Exception) {
            AuthResult.Error(
                e.message ?: "Registration failed."
            )
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): AuthResult {
        return try {

            // 1. Authenticate with Firebase
            val result = auth
                .signInWithEmailAndPassword(email, password)
                .await()

            // 2. Make sure Firebase returned a user
            val uid = result.user?.uid
                ?: return AuthResult.Error("Login failed.")

            // 3. Check that the SurgeX profile exists
            val document = db
                .collection("users")
                .document(uid)
                .get()
                .await()

            if (!document.exists()) {
                auth.signOut()
                return AuthResult.Error(
                    "Your SurgeX profile could not be found."
                )
            }

            // 4. Verify that a role exists
            val role = document.getString("role")

            if (role.isNullOrBlank()) {
                auth.signOut()
                return AuthResult.Error(
                    "Your SurgeX account has no role assigned."
                )
            }

            // Login is successful.
            // The role will be retrieved by the navigation layer
            // in the next step.
            AuthResult.Success

        } catch (e: Exception) {
            AuthResult.Error(
                e.message ?: "Login failed."
            )
        }
    }

    suspend fun getCurrentUserRole(): UserRole? {
        return try {

            val uid = auth.currentUser?.uid
                ?: return null

            val document = db
                .collection("users")
                .document(uid)
                .get()
                .await()

            when (document.getString("role")) {
                UserRole.RIDER.name -> UserRole.RIDER
                UserRole.DRIVER.name -> UserRole.DRIVER
                else -> null
            }

        } catch (e: Exception) {
            null
        }
    }

    fun logout() {
        auth.signOut()
    }
}
