package com.titixoid.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.titixoid.data.models.UserDao
import com.titixoid.data.models.toDomainUser
import com.titixoid.domain.models.AuthStatus
import com.titixoid.domain.models.User
import com.titixoid.domain.repository.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseUserRepository(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val authTokenDataStore: AuthTokenDataStore
) : UserRepository {

    override suspend fun signIn(login: String, password: String): User? {
        return try {
            val querySnapshot = firestore.collection("users")
                .whereEqualTo("login", login)
                .get()
                .await()

            if (querySnapshot.isEmpty) {
                Log.w("FirebaseUserRepo", "User with login $login not found")
                return null
            }

            val userDoc = querySnapshot.documents[0]
            val email = userDoc.getString("email") ?: run {
                Log.w("FirebaseUserRepo", "Email not found for login $login")
                return null
            }

            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: return null

            val token = firebaseUser.getIdToken(false).await().token
            if (!token.isNullOrEmpty()) {
                authTokenDataStore.saveAuthToken(token)
                authTokenDataStore.saveUserEmail(email)
            }

            userDoc.toObject(UserDao::class.java)?.toDomainUser(userDoc.id)
        } catch (e: Exception) {
            when (e) {
                is FirebaseAuthInvalidUserException -> Log.e(
                    "FirebaseUserRepo",
                    "User not found",
                    e
                )

                is FirebaseAuthInvalidCredentialsException -> Log.e(
                    "FirebaseUserRepo",
                    "Invalid password",
                    e
                )

                else -> Log.e("FirebaseUserRepo", "SignIn error", e)
            }
            null
        }
    }
    override fun getWorkersWithTaskCountFlow(): Flow<List<User>> = callbackFlow {
        val listener = firestore.collection("users")
            .whereEqualTo("role", "worker")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val users = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(UserDao::class.java)?.toDomainUser(doc.id)
                } ?: emptyList()

                trySend(users).isSuccess
            }

        awaitClose { listener.remove() }
    }

    override suspend fun updateUserTaskCount(workerId: String, delta: Int) {
        try {
            firestore.collection("users")
                .document(workerId)
                .update("taskCount", FieldValue.increment(delta.toLong()))
                .await()
        } catch (e: Exception) {
            throw Exception("Failed to update task count", e)
        }
    }



    override suspend fun checkAndRefreshAuth(): Boolean {
        val currentUser = firebaseAuth.currentUser ?: return false
        return try {
            val tokenResult = currentUser.getIdToken(false).await()
            tokenResult.token?.let { token ->
                authTokenDataStore.saveAuthToken(token)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getUserRole(): String? {
        val currentUser = firebaseAuth.currentUser ?: return null
        return try {
            val documentSnapshot = firestore.collection("users")
                .document(currentUser.uid)
                .get()
                .await()
            documentSnapshot.getString("role")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getAllUsers(): List<User> {
        val snapshot = firestore.collection("users")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(UserDao::class.java)?.toDomainUser(doc.id)
        }
    }

    override suspend fun getAuthStatus(): AuthStatus {
        return try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                val document = firestore.collection("users")
                    .document(currentUser.uid)
                    .get()
                    .await()

                if (document.exists()) {
                    return AuthStatus(
                        isAuthenticated = true,
                        role = document.getString("role"),
                        userId = currentUser.uid
                    )
                }
            }
            AuthStatus(false, null, null)
        } catch (e: Exception) {
            Log.e("AuthStatus", "Error checking auth status", e)
            AuthStatus(false, null, null)
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
        authTokenDataStore.clearAuthData()
    }

    override suspend fun createUser(user: User): Boolean {
        if (user.password.isEmpty() || user.email.isEmpty() ||
            user.firstName.isEmpty() || user.lastName.isEmpty() ||
            user.login.isEmpty()
        ) {
            Log.e("FirebaseUserRepo", "Неполные данные пользователя")
            return false
        }

        return try {
            val loginQuery = firestore.collection("users")
                .whereEqualTo("login", user.login)
                .get()
                .await()

            if (!loginQuery.isEmpty) {
                throw LoginCollisionException("Логин ${user.login} уже занят")
            }

            val authResult =
                firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
            val firebaseUser = authResult.user ?: return false

            val userMap = hashMapOf(
                "firstName" to user.firstName,
                "lastName" to user.lastName,
                "email" to user.email,
                "login" to user.login,
                "role" to "worker",
                "taskCount" to 0
            )

            firestore.collection("users")
                .document(firebaseUser.uid)
                .set(userMap)
                .await()

            true
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is FirebaseAuthUserCollisionException ->
                    "Email ${user.email} уже используется другим аккаунтом"

                is FirebaseAuthWeakPasswordException ->
                    "Пароль слишком слабый. Минимум 6 символов"

                is LoginCollisionException ->
                    e.message ?: "Логин уже занят"

                else -> "Ошибка создания пользователя: ${e.message}"
            }
            Log.e("FirebaseUserRepo", errorMessage, e)
            throw Exception(errorMessage, e)
        }
    }
}

class LoginCollisionException(message: String) : Exception(message)