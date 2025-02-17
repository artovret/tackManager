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
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun signIn(login: String, password: String): User? {
        return try {
            val snapshot = firestore.collection("users")
                .whereEqualTo("login", login)
                .get()
                .await()

            if (snapshot.isEmpty) return null

            val userDao = snapshot.documents[0].toObject(UserDao::class.java) ?: return null

            firebaseAuth.signInWithEmailAndPassword(userDao.email, password).await()

            userDao.toDomainUser()
        } catch (e: Exception) {
            null
        }
    }
}