package com.titixoid.taskmanager.ui.admin.workers

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titixoid.domain.usecases.GetAllUsersUseCase
import com.titixoid.domain.usecases.GetTasksForWorkerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Immutable
data class WorkerUiModel(
    val id: String,
    val firstName: String,
    val lastName: String,
    val taskCount: Int
)

@Immutable
data class AdminWorkerListUiState(
    val workers: List<WorkerUiModel> = emptyList()
)

class AdminWorkerListViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getTasksForWorkerUseCase: GetTasksForWorkerUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AdminWorkerListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadWorkers()
    }

    private fun loadWorkers() {
        viewModelScope.launch {
            val users = getAllUsersUseCase()
                .filter { it.role == "worker" }

            _uiState.update { currentState ->
                currentState.copy(
                    workers = users.map { user ->
                        WorkerUiModel(
                            id = user.id,
                            firstName = user.firstName,
                            lastName = user.lastName,
                            taskCount = getTasksForWorkerUseCase.invoke(user.id).size
                        )
                    }
                )
            }
        }
    }
}