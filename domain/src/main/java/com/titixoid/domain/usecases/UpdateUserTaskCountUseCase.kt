package com.titixoid.domain.usecases

import com.titixoid.domain.repository.UserRepository

class UpdateUserTaskCountUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(workerId: String, delta: Int) {
        userRepository.updateUserTaskCount(workerId, delta)
    }
}