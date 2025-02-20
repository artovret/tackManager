package com.titixoid.domain.repository

import com.titixoid.domain.models.Task

interface TaskRepository {
    suspend fun getTasksForWorker(workerId: String): List<Task>
    suspend fun getAllTasks(): List<Task>
    suspend fun createTask(task: Task): Boolean
}