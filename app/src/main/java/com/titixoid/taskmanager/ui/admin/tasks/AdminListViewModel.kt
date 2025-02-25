package com.titixoid.taskmanager.ui.admin.tasks

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titixoid.domain.models.Task
import com.titixoid.domain.usecases.GetTasksForWorkerUseCase
import com.titixoid.taskmanager.ui.common.TaskFilterEnum
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.primaryText
import com.titixoid.taskmanager.ui.theme.primaryWhite
import com.titixoid.taskmanager.ui.theme.unselectedButton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update



@Immutable
data class AdminFilterButtonState(
    val filter: TaskFilterEnum,
    val backgroundColor: Color,
    val textColor: Color,
    val textStyle: TextStyle
)

@Immutable
data class AdminTaskListUiState(
    val tasks: List<Task> = emptyList(),
    val selectedFilter: TaskFilterEnum = TaskFilterEnum.NONE,
    val filteredTasks: List<Task> = emptyList(),
    val filterButtonStates: List<AdminFilterButtonState> = emptyList()
) {
    companion object {
        val DEFAULT_FILTERS = listOf(
            TaskFilterEnum.URGENT,
            TaskFilterEnum.PLANNED,
            TaskFilterEnum.OPTIONAL
        )
    }
}

class AdminTaskListViewModel(
    private val workerId: String,
    private val getTasksForWorkerUseCase: GetTasksForWorkerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminTaskListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeTasks()
    }

    private fun observeTasks() {
        getTasksForWorkerUseCase(workerId)
            .onEach { tasks ->
                updateState { currentState ->
                    currentState.copy(
                        tasks = tasks,
                        filteredTasks = filterTasks(tasks, currentState.selectedFilter),
                        filterButtonStates = createFilterButtonStates(currentState.selectedFilter)
                    )
                }
            }
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }

    fun setFilter(filter: TaskFilterEnum) {
        updateState { currentState ->
            val newFilter =
                if (filter == currentState.selectedFilter) TaskFilterEnum.NONE else filter
            currentState.copy(
                selectedFilter = newFilter,
                filteredTasks = filterTasks(currentState.tasks, newFilter),
                filterButtonStates = createFilterButtonStates(newFilter)
            )
        }
    }

    private fun filterTasks(tasks: List<Task>, filter: TaskFilterEnum): List<Task> {
        return when (filter) {
            TaskFilterEnum.NONE -> tasks
            else -> tasks.filter { it.status.equals(filter.id, ignoreCase = true) }
        }
    }

    private fun createFilterButtonStates(selectedFilter: TaskFilterEnum): List<AdminFilterButtonState> {
        return AdminTaskListUiState.DEFAULT_FILTERS.map { filter ->
            AdminFilterButtonState(
                filter = filter,
                backgroundColor = if (filter == selectedFilter) primaryWhite else unselectedButton,
                textColor = primaryText,
                textStyle = if (filter == selectedFilter) Typography.labelLarge else Typography.labelSmall
            )
        }
    }

    private fun updateState(update: (AdminTaskListUiState) -> AdminTaskListUiState) {
        _uiState.update(update)
    }
}