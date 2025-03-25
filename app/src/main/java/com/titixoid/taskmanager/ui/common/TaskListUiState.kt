package com.titixoid.taskmanager.ui.common

import com.titixoid.domain.models.Task

data class TaskListUiState(
    val tasks: List<Task> = emptyList(),
    val selectedFilter: String = "",
    val filterButtonStates: List<FilterButtonState> = emptyList(),
    val filteredTasks: List<Task> = emptyList()
)