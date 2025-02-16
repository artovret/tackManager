package com.titixoid.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.titixoid.taskmanager.ui.TasksNavHost
import com.titixoid.taskmanager.ui.admin.workers.AdminWorkerListViewModel
import com.titixoid.taskmanager.ui.theme.TaskManagerTheme
import com.titixoid.taskmanager.ui.worker.tasks.WorkerTaskListViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: WorkerTaskListViewModel by viewModels()
    private val workerListViewModel: AdminWorkerListViewModel by viewModels()

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