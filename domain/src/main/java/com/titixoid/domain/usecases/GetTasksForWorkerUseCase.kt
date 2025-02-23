package com.titixoid.domain.usecases

import com.titixoid.domain.models.Task
import com.titixoid.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasksForWorkerUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(workerId: String): Flow<List<Task>> {
        return taskRepository.getTasksForWorkerFlow(workerId)
    }
}