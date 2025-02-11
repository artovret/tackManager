package com.titixoid.taskmanager.ui.admin.workers

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.work.Worker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Immutable
data class WorkerUiModel(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val taskCount: Int
)

@Immutable
data class AdminWorkerListUiState(
    val workers: List<WorkerUiModel> = emptyList()
)

class AdminWorkerListViewModel : ViewModel() {

    private val initialWorkers = listOf(
        WorkerUiModel(1, "Иван", "Иванов", 5),
        WorkerUiModel(2, "Пётр", "Петров", 3),
        WorkerUiModel(3, "Сергей", "Сергеев", 12),
        WorkerUiModel(4, "Мария", "Смирнова", 7)
    )

    private val _uiState = MutableStateFlow(
        AdminWorkerListUiState(
            workers = initialWorkers
        )
    )
    val uiState = _uiState.asStateFlow()
}