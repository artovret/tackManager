package com.titixoid.taskmanager.ui.admin.tasks

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titixoid.domain.models.Task
import com.titixoid.domain.usecases.GetTasksForWorkerUseCase
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.primaryText
import com.titixoid.taskmanager.ui.theme.primaryWhite
import com.titixoid.taskmanager.ui.theme.unselectedButton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


enum class AdminTaskFilter(val id: String, val displayName: String) {
    None("", "Все"),
    Urgent("urgent", "Срочные"),
    Planned("planned", "Плановые"),
    Optional("optional", "Доп. задачи")
}

@Immutable
data class AdminFilterButtonState(
    val filter: AdminTaskFilter,
    val backgroundColor: Color,
    val textColor: Color,
    val textStyle: TextStyle
)

@Immutable
data class AdminTaskListUiState(
    val tasks: List<Task> = emptyList(),
    val selectedFilter: AdminTaskFilter = AdminTaskFilter.None,
    val filteredTasks: List<Task> = emptyList(),
    val filterButtonStates: List<AdminFilterButtonState> = emptyList()
) {
    companion object {
        val DEFAULT_FILTERS = listOf(
            AdminTaskFilter.Urgent,
            AdminTaskFilter.Planned,
            AdminTaskFilter.Optional
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
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            val tasks = getTasksForWorkerUseCase(workerId)
            updateState {
                it.copy(
                    tasks = tasks,
                    filteredTasks = tasks,
                    filterButtonStates = createFilterButtonStates(AdminTaskFilter.None)
                )
            }
        }
    }

    fun setFilter(filter: AdminTaskFilter) {
        updateState { currentState ->
            val newFilter =
                if (filter == currentState.selectedFilter) AdminTaskFilter.None else filter
            currentState.copy(
                selectedFilter = newFilter,
                filteredTasks = filterTasks(currentState.tasks, newFilter),
                filterButtonStates = createFilterButtonStates(newFilter)
            )
        }
    }

    private fun filterTasks(tasks: List<Task>, filter: AdminTaskFilter): List<Task> {
        return when (filter) {
            AdminTaskFilter.None -> tasks
            else -> tasks.filter { it.status.equals(filter.id, ignoreCase = true) }
        }
    }

    private fun createFilterButtonStates(selectedFilter: AdminTaskFilter): List<AdminFilterButtonState> {
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