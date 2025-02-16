package com.titixoid.domain.usecases

import com.titixoid.domain.repository.AuthRepository

class SignInUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(login: String, password: String): Boolean {
        return authRepository.signIn(login, password)
    }
}