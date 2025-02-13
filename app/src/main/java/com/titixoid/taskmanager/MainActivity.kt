package com.titixoid.taskmanager

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.titixoid.taskmanager.ui.admin.workers.AdminWorkerListViewModel
import com.titixoid.taskmanager.ui.start.StartScreen
import com.titixoid.taskmanager.ui.theme.TaskManagerTheme
import com.titixoid.taskmanager.ui.worker.tasks.Task
import com.titixoid.taskmanager.ui.worker.tasks.TaskFilter
import com.titixoid.taskmanager.ui.worker.tasks.WorkerTaskListScreen
import com.titixoid.taskmanager.ui.worker.tasks.WorkerTaskListUiState
import com.titixoid.taskmanager.ui.worker.tasks.WorkerTaskListViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: WorkerTaskListViewModel by viewModels()
    private val workerListViewModel: AdminWorkerListViewModel by viewModels()

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagerTheme {
                // Подписываемся на uiState, чтобы получать обновления
//                val uiState by viewModel.uiState.collectAsState()
//                WorkerTaskListScreen(
//                    uiState = uiState,
//                    onFilterSelected = { filter ->
//                        viewModel.setFilter(filter)
//                    },
//                    onTaskClick = { task ->
//                        // Обработка клика по задаче (например, навигация)
//                    }
//                )
//                val uiState by workerListViewModel.uiState.collectAsState()
//                WorkerListScreen(
//                    uiState = uiState,
//                    onWorkerClick = {}
//                )
                StartScreen {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkerTaskListScreenPreview() {
    TaskManagerTheme {
        WorkerTaskListScreen(
            uiState = WorkerTaskListUiState(
                tasks = listOf(
                    Task(1, "Срочная задача 1", "Короткое описание.", TaskFilter.Urgent),
                    Task(2, "Плановая задача 1", "Длинное описание. ".repeat(8).trim(), TaskFilter.Planned),
                    Task(3, "Опциональная задача 1", "Среднее описание. ".repeat(4).trim(), TaskFilter.Optional)
                ),
                selectedFilter = TaskFilter.None,
                filteredTasks = listOf(
                    Task(1, "Срочная задача 1", "Короткое описание.", TaskFilter.Urgent),
                    Task(2, "Плановая задача 1", "Длинное описание. ".repeat(8).trim(), TaskFilter.Planned),
                    Task(3, "Опциональная задача 1", "Среднее описание. ".repeat(4).trim(), TaskFilter.Optional)
                )
            ),
            onFilterSelected = {},
            onTaskClick = {}
        )
    }
}
