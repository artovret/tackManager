package com.titixoid.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.titixoid.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun signIn(login: String, password: String): Boolean {
        return try {
            firebaseAuth.signInWithEmailAndPassword(login, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}