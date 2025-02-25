package com.titixoid.taskmanager.ui.worker.tasks.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.titixoid.domain.models.Task
import com.titixoid.taskmanager.ui.common.TaskFilterEnum
import com.titixoid.taskmanager.ui.worker.tasks.WorkerTaskListScreen
import com.titixoid.taskmanager.ui.worker.tasks.WorkerTaskListViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data class WorkerDestination(val workerId: String)

fun NavGraphBuilder.workerRole() {
    composable<WorkerDestination> { backStackEntry ->
        val args = backStackEntry.toRoute<WorkerDestination>()
        val viewModel: WorkerTaskListViewModel =
            koinViewModel(parameters = { parametersOf(args.workerId) })
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        WorkerTaskListScreen(
            uiState = uiState,
            onFilterSelected = { filter: TaskFilterEnum ->  // Явно указываем тип фильтра
                viewModel.setFilter(filter)
            },
            onTaskClick = { task: Task ->  // Явно указываем тип задачи
                // Реализуйте навигацию к деталям задачи
                // navController.navigate(TaskDetailsDestination(task.id))
            }
        )
    }
}
