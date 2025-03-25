package com.titixoid.domain.usecases

import com.titixoid.domain.models.Task

class DeleteTaskAndUpdateTaskCountUseCase(
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateUserTaskCountUseCase: UpdateUserTaskCountUseCase
) {
    suspend operator fun invoke(task: Task): Boolean {
        val wasDeleted = deleteTaskUseCase(task)
        if (wasDeleted) {
            updateUserTaskCountUseCase(task.workerId, -1)
            return true
        }
        return false
    }
}