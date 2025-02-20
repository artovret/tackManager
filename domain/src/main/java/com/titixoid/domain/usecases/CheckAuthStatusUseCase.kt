package com.titixoid.domain.usecases

import com.titixoid.domain.models.AuthStatus
import com.titixoid.domain.repository.UserRepository


class CheckAuthStatusUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): AuthStatus {
        val isAuth = userRepository.checkAndRefreshAuth()
        val role = if (isAuth) userRepository.getUserRole() else null
        return AuthStatus(isAuthenticated = isAuth, role = role)
    }
}