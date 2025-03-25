package com.titixoid.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.titixoid.taskmanager.ui.TasksNavHost
import com.titixoid.taskmanager.ui.theme.TaskManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskNavApp()
        }
    }
}

@Composable
private fun TaskNavApp() {
    TaskManagerTheme {
        TasksNavHost()
    }
}