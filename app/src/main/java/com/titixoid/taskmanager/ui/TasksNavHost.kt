package com.titixoid.taskmanager.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.titixoid.taskmanager.ui.login.navigation.SignInDestination
import com.titixoid.taskmanager.ui.login.navigation.login
import com.titixoid.taskmanager.ui.start.StartNavigationDestination
import com.titixoid.taskmanager.ui.start.navigation.AdminGraph
import com.titixoid.taskmanager.ui.start.navigation.StartDestination
import com.titixoid.taskmanager.ui.start.navigation.admin
import com.titixoid.taskmanager.ui.start.navigation.start
import com.titixoid.taskmanager.ui.worker.tasks.navigation.WorkerDestination
import com.titixoid.taskmanager.ui.worker.tasks.navigation.workerRole

@Composable
fun TasksNavHost(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = viewModel()
) {
    val authenticated by viewModel.authenticated.collectAsState()
    val adminRole by viewModel.admin.collectAsState()
    val targetDestination = SignInDestination

    NavHost(
        navController = navController,
        startDestination = StartDestination
    ) {
        start { destination ->
            navController.popBackStack()
            when (destination) {
                StartNavigationDestination.Admin -> navController.navigate(AdminGraph)
                StartNavigationDestination.Worker -> navController.navigate(WorkerDestination)
                StartNavigationDestination.Auth -> navController.navigate(SignInDestination)
                StartNavigationDestination.Loading -> {}
            }
        }
        login(navController)
        admin(
            navController = navController,
            onLoginClicked = {},
            onNavigateToResetPasswordClicked = {},
            onNavigateToRegisterClicked = {},
            onRegisterClicked = {},
            onNavigateBack = {},
            onResetPasswordClicked = {}
        )
        workerRole(onStartClicked = {})
    }
}