package com.titixoid.domain.usecases

import com.titixoid.domain.models.Task
import com.titixoid.domain.repository.TaskRepository

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Boolean {
        return taskRepository.deleteTask(task)
    }
}