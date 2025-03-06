package com.titixoid.taskmanager.ui.admin.task_create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titixoid.domain.models.Task
import com.titixoid.domain.usecases.CreateTaskUseCase
import com.titixoid.domain.usecases.UpdateUserTaskCountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class CreateTaskUiState(
    val title: String = "",
    val description: String = "",
    val status: String = "",
    val workerId: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = title.isNotEmpty() && description.isNotEmpty() && status.isNotEmpty()
}

class CreateTaskViewModel(
    workerId: String,
    private val createTaskUseCase: CreateTaskUseCase,
    private val updateTaskCountUseCase: UpdateUserTaskCountUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateTaskUiState(workerId = workerId))
    val uiState = _uiState

    fun onTitleChange(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun onStatusChange(status: String) {
        _uiState.value = _uiState.value.copy(status = status)
    }

    fun createTask(onResult: (Boolean) -> Unit) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            try {
                val task = Task(
                    id = "",
                    title = _uiState.value.title,
                    description = _uiState.value.description,
                    status = _uiState.value.status,
                    completed = false,
                    workerId = _uiState.value.workerId
                )
                val result = createTaskUseCase(task)
                updateTaskCountUseCase(_uiState.value.workerId, 1)
                _uiState.value = _uiState.value.copy(isLoading = false)
                onResult(result)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Ошибка при создании задачи"
                )
            }
        }
    }
}