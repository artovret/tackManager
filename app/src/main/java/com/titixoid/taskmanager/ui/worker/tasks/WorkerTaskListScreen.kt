package com.titixoid.taskmanager.ui.worker.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.titixoid.domain.models.Task
import com.titixoid.taskmanager.ui.common.TaskFilterEnum
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.primaryText
import com.titixoid.taskmanager.ui.theme.primaryWhite
import com.titixoid.taskmanager.ui.theme.secondText
import com.titixoid.taskmanager.ui.theme.space16
import com.titixoid.taskmanager.ui.theme.space32
import com.titixoid.taskmanager.ui.widgets.BackgroundColumn
import com.titixoid.taskmanager.ui.widgets.BottomBarInsetsSpacer
import com.titixoid.taskmanager.ui.widgets.StatusBarInsetsSpacer


@Composable
fun WorkerTaskListScreen(
    uiState: WorkerTaskListUiState,
    onFilterSelected: (TaskFilterEnum) -> Unit,
    onTaskClick: (Task) -> Unit
) {
    BackgroundColumn {
        StatusBarInsetsSpacer()
        Spacer(modifier = Modifier.height(space16))

        Text(
            text = "Список задач",
            style = Typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(space16))
        Text(
            text = "Приятного дня",
            style = Typography.bodySmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(space16))

        Row(modifier = Modifier.fillMaxWidth()) {
            uiState.filterButtonStates.forEachIndexed { index, buttonState ->
                Button(
                    onClick = { onFilterSelected(buttonState.filter) },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(50.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .background(
                                SolidColor(buttonState.backgroundColor),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = buttonState.filter.displayName,
                            style = buttonState.textStyle,
                            color = buttonState.textColor,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                if (index < uiState.filterButtonStates.size - 1) {
                    Spacer(modifier = Modifier.width(space16))
                }
            }
        }
        Spacer(modifier = Modifier.height(space32))

        Column(modifier = Modifier.fillMaxWidth()) {
            if (uiState.filteredTasks.isEmpty()) {
                Text(
                    text = "Нет задач",
                    style = Typography.bodySmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                uiState.filteredTasks.forEach { task ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(
                                color = primaryWhite,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onTaskClick(task) }
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(
                                text = task.title,
                                style = Typography.bodyLarge,
                                color = primaryText
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = task.description,
                                style = Typography.bodySmall,
                                color = secondText
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        BottomBarInsetsSpacer()
    }
}