package com.titixoid.domain.repository

import com.titixoid.domain.models.User

interface AuthRepository {
    suspend fun signIn(login: String, password: String): User?
    suspend fun checkAndRefreshAuth(): Boolean
    suspend fun getUserRole(): String?
}