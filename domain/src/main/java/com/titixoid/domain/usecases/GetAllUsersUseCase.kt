package com.titixoid.domain.usecases

import com.titixoid.domain.models.User
import com.titixoid.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetAllUsersUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<List<User>> {
        return userRepository.getWorkersWithTaskCountFlow()
    }
}