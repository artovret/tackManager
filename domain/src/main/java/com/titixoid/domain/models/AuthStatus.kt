package com.titixoid.domain.models

data class AuthStatus(
    val isAuthenticated: Boolean,
    val role: String? = null
)