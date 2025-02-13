package com.titixoid.taskmanager.ui.start.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.titixoid.taskmanager.ui.admin.tasks.AdminTaskListScreen
import com.titixoid.taskmanager.ui.admin.tasks.AdminTaskListUiState
import com.titixoid.taskmanager.ui.admin.tasks.TaskFilter
import com.titixoid.taskmanager.ui.admin.workers.AdminWorkerListUiState
import com.titixoid.taskmanager.ui.admin.workers.WorkerListScreen
import kotlinx.serialization.Serializable

@Serializable
object AdminGraph

@Serializable
private object WorkersDestination

@Serializable
private object TaskListDestination

@Serializable
private object TaskCreateDestination

fun NavGraphBuilder.admin(
    onLoginClicked: () -> Unit,
    onNavigateToResetPasswordClicked: () -> Unit,
    onNavigateToRegisterClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    onNavigateBack: () -> Unit,
    onResetPasswordClicked: () -> Unit
) {
    navigation<AdminGraph>(startDestination = WorkersDestination) {
        composable<WorkersDestination> {
            WorkerListScreen(
                uiState = AdminWorkerListUiState(workers = listOf()),
                onWorkerClick = {}
            )
        }
        composable<TaskListDestination> {
            AdminTaskListScreen(uiState = AdminTaskListUiState(
                tasks = listOf(),
                selectedFilter = TaskFilter.None,
                availableFilters = listOf(),
                filteredTasks = listOf(),
                filterButtonStates = listOf()
            ), onFilterSelected = {}, onTaskClick = {}
            )
        }
//        composable<ResetPasswordDestination> {
//            ResetPasswordScreen(
//                onResetPasswordClicked = onResetPasswordClicked,
//                onNavigateBack = onNavigateBack
//            )
//        }
    }
}