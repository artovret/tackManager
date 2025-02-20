package com.titixoid.taskmanager.ui.admin.task_create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titixoid.domain.models.Task
import com.titixoid.domain.usecases.CreateTaskUseCase
import kotlinx.coroutines.launch

data class CreateTaskUiState(
    val title: String = "",
    val description: String = "",
    val status: String = "",
    val workerId: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class CreateTaskViewModel(
    private val createTaskUseCase: CreateTaskUseCase
) : ViewModel() {

    fun createTask(
        title: String,
        description: String,
        status: String,
        workerId: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val task = Task(
                id = "",
                title = title,
                description = description,
                status = status,
                completed = false,
                workerId = workerId
            )
            val result = createTaskUseCase(task)
            onResult(result)
        }
    }
}