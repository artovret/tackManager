package com.titixoid.taskmanager.ui.worker.tasks

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titixoid.domain.models.Task
import com.titixoid.domain.usecases.GetTasksForWorkerUseCase
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.widgets.TaskFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//sealed class TaskFilter(open val displayName: String) {
//    data object None : TaskFilter("Все")
//    data object Urgent : TaskFilter("Срочные")
//    data object Planned : TaskFilter("Плановые")
//    data object Optional : TaskFilter("Прочее")
//}

//@Immutable
//data class Task(
//    val id: Int,
//    val title: String,
//    val description: String,
//    val filter: TaskFilter
//)

@Immutable
data class FilterButtonState(
    val filter: TaskFilter,
    val backgroundColor: Color,
    val textColor: Color,
    val textStyle: TextStyle
)

//@Immutable
//data class WorkerTaskListUiState(
//    val tasks: List<Task> = emptyList(),
//    val selectedFilter: TaskFilter = TaskFilter.None,
//    val availableFilters: List<TaskFilter> = listOf(
//        TaskFilter.Urgent,
//        TaskFilter.Planned,
//        TaskFilter.Optional
//    ),
//    val filteredTasks: List<Task> = emptyList(),
//    val filterButtonStates: List<FilterButtonState> = emptyList()
//)

@Immutable
data class WorkerTaskListUiState(
    val tasks: List<Task> = emptyList(),
    val selectedFilter: TaskFilter = TaskFilter.NONE,
    val availableFilters: List<TaskFilter> = listOf(
        TaskFilter.IN_PROGRESS,
        TaskFilter.COMPLETED,
        TaskFilter.PENDING
    ),
    val filteredTasks: List<Task> = emptyList(),
    val filterButtonStates: List<FilterButtonState> = emptyList()
)

class WorkerTaskListViewModel(
    private val workerId: String,
    private val getTasksForWorkerUseCase: GetTasksForWorkerUseCase
) : ViewModel() {

    private val selectedBackgroundColor = Color.White
    private val unselectedBackgroundColor = Color.LightGray
    private val buttonTextColor = Color.Black

    private val _uiState = MutableStateFlow(WorkerTaskListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch {
            getTasksForWorkerUseCase(workerId)
                .onEach { tasks ->
                    _uiState.update { current ->
                        current.copy(
                            tasks = tasks,
                            filteredTasks = if (current.selectedFilter == TaskFilter.NONE) tasks
                            else tasks.filter {
                                it.status.equals(
                                    current.selectedFilter.id,
                                    ignoreCase = true
                                )
                            },
                            filterButtonStates = computeFilterButtonStates(
                                current.selectedFilter,
                                current.availableFilters
                            )
                        )
                    }
                }
                .catch { e -> e.printStackTrace() }
        }
    }

    fun setFilter(filter: TaskFilter) {
        _uiState.update { current ->
            val newFilter = if (current.selectedFilter == filter) TaskFilter.NONE else filter
            val newFilteredTasks = if (newFilter == TaskFilter.NONE) {
                current.tasks
            } else {
                current.tasks.filter { it.status.equals(newFilter.id, ignoreCase = true) }
            }
            val newFilterButtonStates = computeFilterButtonStates(newFilter, current.availableFilters)
            current.copy(
                selectedFilter = newFilter,
                filteredTasks = newFilteredTasks,
                filterButtonStates = newFilterButtonStates
            )
        }
    }

    private fun computeFilterButtonStates(
        selected: TaskFilter,
        available: List<TaskFilter>
    ): List<FilterButtonState> {
        return available.map { filter ->
            if (filter == selected) {
                FilterButtonState(
                    filter = filter,
                    backgroundColor = selectedBackgroundColor,
                    textColor = buttonTextColor,
                    textStyle = Typography.labelLarge
                )
            } else {
                FilterButtonState(
                    filter = filter,
                    backgroundColor = unselectedBackgroundColor,
                    textColor = buttonTextColor,
                    textStyle = Typography.labelSmall
                )
            }
        }
    }
}