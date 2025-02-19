package com.titixoid.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.titixoid.data.models.UserDao
import com.titixoid.data.models.toDomainUser
import com.titixoid.domain.models.User
import com.titixoid.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val authTokenDataStore: AuthTokenDataStore
) : AuthRepository {

    override suspend fun signIn(login: String, password: String): User? {
        return try {
            val snapshot = firestore.collection("users")
                .whereEqualTo("login", login)
                .get()
                .await()

            if (snapshot.isEmpty) return null

            val userDao = snapshot.documents[0].toObject(UserDao::class.java) ?: return null

            val authResult =
                firebaseAuth.signInWithEmailAndPassword(userDao.email, password).await()
            val firebaseUser = authResult.user

            val idTokenResult = firebaseUser?.getIdToken(false)?.await()
            val token = idTokenResult?.token

            if (!token.isNullOrEmpty()) {
                authTokenDataStore.saveAuthToken(token)
            }

            userDao.toDomainUser()
        } catch (e: Exception) {
            null
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
}