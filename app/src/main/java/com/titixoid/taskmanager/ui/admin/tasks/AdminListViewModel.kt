package com.titixoid.taskmanager.ui.admin.tasks

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titixoid.domain.models.Task
import com.titixoid.domain.usecases.GetTasksForWorkerUseCase
import com.titixoid.taskmanager.ui.theme.Typography
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed class TaskFilter(open val id: String, open val displayName: String) {
    data object None : TaskFilter("all", "Все")
    data object Urgent : TaskFilter("urgent", "Срочные")
    data object Planned : TaskFilter("planned", "Плановые")
    data object Optional : TaskFilter("optional", "Доп. задачи")
}

@Immutable
data class FilterButtonState(
    val filter: TaskFilter,
    val backgroundColor: Color,
    val textColor: Color,
    val textStyle: TextStyle
)

@Immutable
data class AdminTaskListUiState(
    val tasks: List<Task> = emptyList(),
    val selectedFilter: TaskFilter = TaskFilter.None,
    val availableFilters: List<TaskFilter> = listOf(
        TaskFilter.Urgent,
        TaskFilter.Planned,
        TaskFilter.Optional
    ),
    val filteredTasks: List<Task> = emptyList(),
    val filterButtonStates: List<FilterButtonState> = emptyList()
)

class AdminTaskListViewModel(
    private val workerId: String,
    private val getTasksForWorkerUseCase: GetTasksForWorkerUseCase
) : ViewModel() {

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            val tasks = getTasksForWorkerUseCase(workerId)
            _uiState.update { currentState ->
                currentState.copy(
                    tasks = tasks,
                    filteredTasks = tasks,
                    filterButtonStates = computeFilterButtonStates(
                        selected = currentState.selectedFilter,
                        available = currentState.availableFilters
                    )
                )
            }
        }
    }

    private val selectedBackgroundColor = Color.White
    private val unselectedBackgroundColor = Color.LightGray
    private val buttonTextColor = Color.Black

    private val _uiState = MutableStateFlow(AdminTaskListUiState())
    val uiState = _uiState.asStateFlow()

    private fun computeFilterButtonStates(
        selected: TaskFilter,
        available: List<TaskFilter>
    ): List<FilterButtonState> {
        return available.map { filter ->
            if (filter == selected) {
                FilterButtonState(
                    filter,
                    selectedBackgroundColor,
                    buttonTextColor,
                    Typography.labelLarge
                )
            } else {
                FilterButtonState(
                    filter,
                    unselectedBackgroundColor,
                    buttonTextColor,
                    Typography.labelSmall
                )
            }
        }
    }

    fun setFilter(filter: TaskFilter) {
        _uiState.update { current ->
            val newFilter = if (filter == current.selectedFilter) {
                TaskFilter.None
            } else {
                filter
            }

            val newFilteredTasks = when (newFilter) {
                TaskFilter.None -> current.tasks
                else -> current.tasks.filter { task ->
                    task.status.equals(newFilter.id, ignoreCase = true)
                }
            }

            current.copy(
                selectedFilter = newFilter,
                filteredTasks = newFilteredTasks,
                filterButtonStates = computeFilterButtonStates(
                    selected = newFilter,
                    available = current.availableFilters
                )
            )
        }
    }

}