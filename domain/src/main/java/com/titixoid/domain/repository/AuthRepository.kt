package com.titixoid.domain.repository

interface AuthRepository {
    suspend fun signIn(login: String, password: String): Boolean
}