package com.titixoid.taskmanager.ui.login.navigation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.titixoid.taskmanager.ui.login.SignInScreen
import com.titixoid.taskmanager.ui.login.SignInViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object SignInDestination

fun NavGraphBuilder.login(onLoginClicked: () -> Unit) {
    composable<SignInDestination> {
        Log.d("NavDebug", "SignInDestination route: ${SignInDestination::class.simpleName}")
        val viewModel: SignInViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        SignInScreen(
            signInUiState = uiState,
            onEmailChanged = viewModel::updateEmail,
            onPasswordChanged = viewModel::updatePassword,
            onLoginClicked = {
                viewModel.signIn { success ->
                    if (success) {
                        Log.d("SignInViewModel", "Login success")
                        onLoginClicked()
                    } else {
                        Log.d("SignInViewModel", "Login failed")
                    }
                }
            }
        )
    }
}
