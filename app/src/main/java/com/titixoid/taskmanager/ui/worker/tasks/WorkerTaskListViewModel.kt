package com.titixoid.taskmanager.ui.worker.tasks

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.titixoid.taskmanager.ui.theme.Typography

sealed class TaskFilter(open val displayName: String) {
    data object None : TaskFilter("Все")
    data object Urgent : TaskFilter("Срочные")
    data object Planned : TaskFilter("Плановые")
    data object Optional : TaskFilter("Прочее")
}

@Immutable
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val filter: TaskFilter
)

@Immutable
data class FilterButtonState(
    val filter: TaskFilter,
    val backgroundColor: Color,
    val textColor: Color,
    val textStyle: TextStyle
)

@Immutable
data class WorkerTaskListUiState(
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

class WorkerTaskListViewModel : ViewModel() {
    private val initialTasks = listOf(
        Task(1, "Срочная задача 1", "Описание срочной задачи 1", TaskFilter.Urgent),
        Task(2, "Плановая задача 1", "Описание плановой задачи 1", TaskFilter.Planned),
        Task(3, "Опциональная задача 1", "Описание опциональной задачи 1", TaskFilter.Optional),
        Task(4, "Срочная задача 2", "Описание срочной задачи 2", TaskFilter.Urgent),
        Task(5, "Плановая задача 2", "Описание плановой задачи 2", TaskFilter.Planned),
        Task(6, "Опциональная задача 2", "Описание опциональной задачи 2", TaskFilter.Optional)
    )

    private val selectedBackgroundColor = Color.White
    private val unselectedBackgroundColor = Color.LightGray
    private val buttonTextColor = Color.Black

    private val _uiState = MutableStateFlow(
        WorkerTaskListUiState(
            tasks = initialTasks,
            selectedFilter = TaskFilter.None,
            filteredTasks = initialTasks,
            filterButtonStates = computeFilterButtonStates(
                TaskFilter.None,
                listOf(TaskFilter.Urgent, TaskFilter.Planned, TaskFilter.Optional)
            )
        )
    )
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
            val newFilter = if (current.selectedFilter == filter) TaskFilter.None else filter
            val newFilteredTasks = if (newFilter == TaskFilter.None) {
                current.tasks
            } else {
                current.tasks.filter { it.filter == newFilter }
            }
            val newFilterButtonStates = computeFilterButtonStates(newFilter, current.availableFilters)
            current.copy(
                selectedFilter = newFilter,
                filteredTasks = newFilteredTasks,
                filterButtonStates = newFilterButtonStates
            )
        }
    }
}