package com.titixoid.domain.repository

import com.titixoid.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasksForWorkerFlow(workerId: String): Flow<List<Task>>
    suspend fun getAllTasks(): List<Task>
    suspend fun createTask(task: Task): Boolean
}