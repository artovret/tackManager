package com.titixoid.taskmanager.ui.start.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.titixoid.domain.models.StartNavigationDestination
import com.titixoid.taskmanager.ui.start.StartScreen
import com.titixoid.taskmanager.ui.start.StartScreenViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object StartDestination

fun NavGraphBuilder.start(
    onNavigate: (StartNavigationDestination) -> Unit
) {
    composable<StartDestination> {
        val viewModel: StartScreenViewModel = koinViewModel()
        LaunchedEffect(viewModel) {
            viewModel.navigationState.collect { destination ->
                if (destination !is StartNavigationDestination.Loading) {
                    Log.d("NavDebug", "Получено направление: $destination")
                    onNavigate(destination)
                }
            }
        }
        StartScreen()
    }
}