package com.titixoid.data.repository

import com.google.firebase.auth.FirebaseAuth
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
            val snapshot = firestore.collection("users")
                .whereEqualTo("login", login)
                .get()
                .await()

            if (snapshot.isEmpty) return null


            val document = snapshot.documents[0]
            val userDao = document.toObject(UserDao::class.java) ?: return null

            val authResult =
                firebaseAuth.signInWithEmailAndPassword(userDao.email, password).await()
            val firebaseUser = authResult.user

            val idTokenResult = firebaseUser?.getIdToken(false)?.await()
            val token = idTokenResult?.token

            if (!token.isNullOrEmpty()) {
                authTokenDataStore.saveAuthToken(token)
            }

            userDao.toDomainUser(document.id)
        } catch (e: Exception) {
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
                val role = getCachedUserRole(currentUser.uid)
                AuthStatus(
                    isAuthenticated = true,
                    role = role,
                    userId = currentUser.uid
                )
            } else {
                AuthStatus(false, null, null)
            }
        } catch (e: Exception) {
            AuthStatus(false, null, null)
        }
    }

    private suspend fun getCachedUserRole(userId: String): String? {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            document.getString("role")
        } catch (e: Exception) {
            null
        }
    }
}