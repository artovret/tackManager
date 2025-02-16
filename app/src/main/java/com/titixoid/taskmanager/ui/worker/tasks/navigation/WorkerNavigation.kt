package com.titixoid.taskmanager.ui.worker.tasks.navigation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.titixoid.taskmanager.ui.worker.tasks.WorkerTaskListScreen
import com.titixoid.taskmanager.ui.worker.tasks.WorkerTaskListViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object WorkerDestination

fun NavGraphBuilder.workerRole(onStartClicked: () -> Unit) {
    composable<WorkerDestination> {
        Log.d("NavDebug", "WorkerDestination route: ${WorkerDestination::class.simpleName}")
        val viewModel: WorkerTaskListViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        WorkerTaskListScreen(
            uiState = uiState,
            onFilterSelected = { filter ->
                viewModel.setFilter(filter)
            },
            onTaskClick = { task ->
                // Обработка клика по задаче (например, навигация)
            }
        )
    }
}
