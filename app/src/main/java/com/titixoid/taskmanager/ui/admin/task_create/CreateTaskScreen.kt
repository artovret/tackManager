package com.titixoid.taskmanager.ui.admin.task_create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.titixoid.taskmanager.ui.theme.Typography

@Composable
fun CreateTaskScreen(
    uiState: CreateTaskUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onStatusChange: (String) -> Unit,
    onWorkerIdChange: (String) -> Unit,
    onCreateTask: () -> Unit,
    onCancel: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.title,
            onValueChange = onTitleChange,
            label = { Text("Заголовок") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.description,
            onValueChange = onDescriptionChange,
            label = { Text("Описание") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.status,
            onValueChange = onStatusChange,
            label = { Text("Статус (urgent, planned, optional)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.workerId,
            onValueChange = onWorkerIdChange,
            label = { Text("Worker ID") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = onCancel) {
                Text("Отмена", style = Typography.bodySmall)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onCreateTask
            ) {
                Text("Создать задачу", style = Typography.bodySmall)
            }
        }
    }
}