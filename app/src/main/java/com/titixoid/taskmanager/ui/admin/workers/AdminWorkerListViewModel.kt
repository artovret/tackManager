package com.titixoid.taskmanager.ui.admin.workers

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titixoid.domain.usecases.GetAllUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

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
    private val getAllUsersUseCase: GetAllUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminWorkerListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeUsers()
    }

    private fun observeUsers() {
        getAllUsersUseCase()
            .onEach { users ->
                val workerUiModels = users.map { user ->
                    WorkerUiModel(
                        id = user.id,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        taskCount = user.taskCount
                    )
                }
                _uiState.update { it.copy(workers = workerUiModels) }
            }
            .launchIn(viewModelScope)
    }
}