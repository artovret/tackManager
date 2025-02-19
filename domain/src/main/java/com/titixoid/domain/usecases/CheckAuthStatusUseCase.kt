package com.titixoid.domain.usecases

import com.titixoid.domain.models.AuthStatus
import com.titixoid.domain.repository.AuthRepository


class CheckAuthStatusUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): AuthStatus {
        val isAuth = authRepository.checkAndRefreshAuth()
        val role = if (isAuth) authRepository.getUserRole() else null
        return AuthStatus(isAuthenticated = isAuth, role = role)
    }
}