package com.titixoid.taskmanager.ui.start.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
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
private object WorkersDestination

@Serializable
data class TaskListDestination(val workerId: String)

@Serializable
private object TaskCreateDestination

fun NavGraphBuilder.admin(
    navController: NavHostController,
    onLoginClicked: () -> Unit,
    onNavigateToResetPasswordClicked: () -> Unit,
    onNavigateToRegisterClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    onNavigateBack: () -> Unit,
    onResetPasswordClicked: () -> Unit
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
            val workerId = args.workerId

            val viewModel: AdminTaskListViewModel = koinViewModel(parameters = {
                parametersOf(args.workerId)
            })

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            AdminTaskListScreen(
                uiState = uiState,
                onFilterSelected = viewModel::setFilter,
                onTaskClick = {}
            )
        }
    }
}

//private fun computeFilterStates(selected: TaskFilter): List<FilterButtonState> {
//    return (TaskFilter.entries - TaskFilter.None).map {
//        FilterButtonState(
//            filter = it,
//            backgroundColor = if (it == selected) Color.White else Color.LightGray,
//            textColor = Color.Black,
//            textStyle = if (it == selected) Typography.labelLarge else Typography.labelSmall
//        )
//    }
//}