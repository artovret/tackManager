package com.titixoid.taskmanager.ui.login.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.titixoid.taskmanager.ui.login.SignInScreen
import com.titixoid.taskmanager.ui.login.SignInViewModel
import com.titixoid.taskmanager.ui.start.navigation.AdminGraph
import com.titixoid.taskmanager.ui.worker.tasks.navigation.WorkerDestination
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object SignInDestination

fun NavGraphBuilder.login(navController: NavHostController) {
    composable<SignInDestination> {
        val viewModel: SignInViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(uiState.isSuccess, uiState.role) {
            if (uiState.isSuccess == true) {
                val destination = if (uiState.role == "admin") AdminGraph else WorkerDestination
                navController.popBackStack()
                navController.navigate(destination)
                viewModel.resetAuthState()
            }
        }

        SignInScreen(
            signInUiState = uiState,
            onEmailChanged = viewModel::updateEmail,
            onPasswordChanged = viewModel::updatePassword,
            onLoginClicked = viewModel::signIn
        )
    }
}