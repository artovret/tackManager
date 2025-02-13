package com.titixoid.taskmanager.ui.start.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.titixoid.taskmanager.ui.start.StartScreen
import kotlinx.serialization.Serializable

@Serializable
object StartDestination

fun NavGraphBuilder.start(onStartClicked: () -> Unit) {
    Log.d("NavDebug", "NavGraphBuilder.start")
    composable<StartDestination> {
        Log.d("NavDebug", "StartDestination route: ${StartDestination::class.simpleName}")
        StartScreen(onStartClicked = onStartClicked)
    }
}
