package com.titixoid.taskmanager.ui.start.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.titixoid.taskmanager.ui.admin.task_create.CreateTaskScreen
import com.titixoid.taskmanager.ui.admin.task_create.CreateTaskViewModel
import com.titixoid.taskmanager.ui.admin.tasks.AdminTaskListScreen
import com.titixoid.taskmanager.ui.admin.tasks.AdminTaskListViewModel
import com.titixoid.taskmanager.ui.admin.workers.AdminWorkerListScreen
import com.titixoid.taskmanager.ui.admin.workers.AdminWorkerListViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
object AdminGraph

@Serializable
object WorkersDestination

@Serializable
data class TaskListDestination(val workerId: String)

@Serializable
class TaskCreateDestination(val workerId: String)

fun NavGraphBuilder.admin(
    navController: NavHostController
) {
    navigation<AdminGraph>(startDestination = WorkersDestination) {
        composable<WorkersDestination> {
            val viewModel: AdminWorkerListViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            AdminWorkerListScreen(
                uiState = uiState,
                onWorkerClick = { workerId ->
                    navController.navigate(TaskListDestination(workerId))
                }
            )
        }
        composable<TaskListDestination> { backStackEntry ->
            val args = backStackEntry.toRoute<TaskListDestination>()
            val viewModel: AdminTaskListViewModel = koinViewModel(parameters = {
                parametersOf(args.workerId)
            })
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            AdminTaskListScreen(
                uiState = uiState,
                onFilterSelected = viewModel::setFilter,
                onAddClicked = { navController.navigate(TaskCreateDestination(args.workerId)) },
                onDeleteClicked = viewModel::deleteTask,
            )
        }

        composable<TaskCreateDestination> { backStackEntry ->
            val args = backStackEntry.toRoute<TaskCreateDestination>()
            val viewModel: CreateTaskViewModel = koinViewModel(parameters = {
                parametersOf(args.workerId)
            })
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            CreateTaskScreen(
                uiState = uiState,
                onTitleChange = viewModel::onTitleChange,
                onDescriptionChange = viewModel::onDescriptionChange,
                onStatusChange = viewModel::onStatusChange,
                onCreateTask = {
                    viewModel.createTask { isSuccess ->
                        if (isSuccess) {
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    }
}
