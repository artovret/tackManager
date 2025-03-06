package com.titixoid.domain.models

data class User(
    val id: String,
    val login: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    val role: String,
    val taskCount: Int,
)