package com.titixoid.domain.models

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val status: String,
    val completed: Boolean,
    val workerId: String
)