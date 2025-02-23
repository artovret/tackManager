package com.titixoid.domain.repository

import com.titixoid.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun signIn(login: String, password: String): User?
    suspend fun checkAndRefreshAuth(): Boolean
    suspend fun getUserRole(): String?
    suspend fun getAllUsers(): List<User>
    fun getWorkersWithTaskCountFlow(): Flow<List<User>>
    suspend fun updateUserTaskCount(workerId: String, delta: Int)
}