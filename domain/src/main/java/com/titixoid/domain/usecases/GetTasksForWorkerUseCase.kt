package com.titixoid.domain.usecases

import com.titixoid.domain.models.Task
import com.titixoid.domain.repository.TaskRepository

class GetTasksForWorkerUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(workerId: String): List<Task> {
        return taskRepository.getTasksForWorker(workerId)
    }
}