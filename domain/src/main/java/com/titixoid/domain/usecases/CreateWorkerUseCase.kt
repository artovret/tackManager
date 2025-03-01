package com.titixoid.domain.usecases

import com.titixoid.domain.models.User
import com.titixoid.domain.repository.UserRepository

class CreateWorkerUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): Boolean {
        return userRepository.createUser(user)
    }
}