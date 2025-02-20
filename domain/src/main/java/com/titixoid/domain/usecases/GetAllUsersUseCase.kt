package com.titixoid.domain.usecases

import com.titixoid.domain.models.User
import com.titixoid.domain.repository.UserRepository

class GetAllUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<User> {
        return userRepository.getAllUsers()
    }
}