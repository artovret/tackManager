package com.titixoid.taskmanager.ui.login.navigation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.titixoid.taskmanager.ui.login.SignInScreen
import com.titixoid.taskmanager.ui.login.SignInViewModel
import kotlinx.serialization.Serializable

@Serializable
object SignInDestination

fun NavGraphBuilder.login(onLoginClicked: () -> Unit) {
    composable<SignInDestination> {
        Log.d("NavDebug", "SignInDestination route: ${SignInDestination::class.simpleName}")
        val viewModel: SignInViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        SignInScreen(
            signInUiState = uiState,
            onEmailChanged = {},
            onPasswordChanged = {},
            onLoginClicked = onLoginClicked
        )
    }
}
