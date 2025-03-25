package com.titixoid.domain.repository

import com.titixoid.domain.models.AuthStatus
import com.titixoid.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getAuthStatus(): AuthStatus
    suspend fun signIn(login: String, password: String): User?
    suspend fun checkAndRefreshAuth(): Boolean
    suspend fun getUserRole(): String?
    suspend fun getAllUsers(): List<User>
    fun getWorkersWithTaskCountFlow(): Flow<List<User>>
    suspend fun updateUserTaskCount(workerId: String, delta: Int)
    suspend fun createUser(user: User): Boolean
}