package com.titixoid.taskmanager.ui

import androidx.compose.runtime.Composable
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
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = StartDestination
    ) {
        start { destination ->
            navController.popBackStack()
            when (destination) {
                StartNavigationDestination.Admin -> navController.navigate(AdminGraph)
                StartNavigationDestination.Auth -> navController.navigate(SignInDestination)
                is StartNavigationDestination.Worker ->
                    navController.navigate(WorkerDestination(destination.workerId))

                StartNavigationDestination.Loading -> TODO()
            }
        }
        login(navController)
        admin(navController = navController)
        workerRole()
    }
}