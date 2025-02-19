package com.titixoid.domain.usecases

import com.titixoid.domain.models.StartNavigationDestination
import com.titixoid.domain.repository.AuthRepository

class CheckAuthStatusAndRoleUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): StartNavigationDestination {
        if (!authRepository.checkAndRefreshAuth()) {
            return StartNavigationDestination.Auth
        }

        val role = authRepository.getUserRole() ?: "worker"
        return if (role.equals("admin", ignoreCase = true)) {
            StartNavigationDestination.Admin
        } else {
            StartNavigationDestination.Worker
        }
    }
}