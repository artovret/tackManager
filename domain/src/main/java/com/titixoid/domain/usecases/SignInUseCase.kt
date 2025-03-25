package com.titixoid.domain.usecases

import com.titixoid.domain.models.User
import com.titixoid.domain.repository.UserRepository

class SignInUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(login: String, password: String): User? {
        return userRepository.signIn(login, password)
    }
}