package com.titixoid.domain.usecases

import com.titixoid.domain.models.User
import com.titixoid.domain.repository.AuthRepository

class SignInUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(login: String, password: String): User? {
        return authRepository.signIn(login, password)
    }
}