package com.titixoid.taskmanager.ui.worker.tasks.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.titixoid.taskmanager.ui.worker.tasks.WorkerTaskListScreen
import com.titixoid.taskmanager.ui.worker.tasks.WorkerTaskListViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data class WorkerHomeDestination(val workerId: String)

fun NavGraphBuilder.workerRole() {
    composable<WorkerHomeDestination> { backStackEntry ->
        val args = backStackEntry.toRoute<WorkerHomeDestination>()
        val viewModel: WorkerTaskListViewModel =
            koinViewModel(parameters = { parametersOf(args.workerId) })
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        WorkerTaskListScreen(
            uiState = uiState,
            onFilterSelected = { filter ->
                viewModel.setFilter(filter)
            },
            onTaskClick = { task ->
                // навигация к деталям задачи
                // navController.navigate(TaskDetailsDestination(task.id))
            }
        )
    }
}
