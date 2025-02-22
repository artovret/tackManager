package com.titixoid.taskmanager.ui.admin.task_create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.titixoid.taskmanager.ui.theme.Gradients
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.cornerRadius8
import com.titixoid.taskmanager.ui.theme.gradientTop
import com.titixoid.taskmanager.ui.theme.primaryText
import com.titixoid.taskmanager.ui.theme.secondaryGray
import com.titixoid.taskmanager.ui.theme.unselectedButton

@Composable
fun CreateTaskScreen(
    uiState: CreateTaskUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onStatusChange: (String) -> Unit,
    onCreateTask: () -> Unit,
    onCancel: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Градиентный фон
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Gradients.MainGradient)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Создание задачи",
                    style = Typography.titleSmall.copy(color = Color.White)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                Text(
                    "Название",
                    style = Typography.labelSmall.copy(color = Color.White),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                BasicTextField(
                    value = uiState.title,
                    onValueChange = onTitleChange,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = Typography.headlineLarge.copy(color = Color.White),
                    cursorBrush = SolidColor(Color.White),
                    decorationBox = { innerTextField ->
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            ) {
                                if (uiState.title.isEmpty()) {
                                    Text(
                                        text = "Введите название",
                                        style = Typography.headlineLarge,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                }
                                innerTextField()
                            }
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Color.White.copy(alpha = 0.3f))
                            )
                        }
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 250.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Описание",
                style = Typography.labelSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = uiState.description,
                onValueChange = onDescriptionChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = secondaryGray,
                    focusedBorderColor = gradientTop
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Приоритет",
                style = Typography.labelSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            PrioritySelector(
                selectedPriority = uiState.status,
                onPrioritySelected = onStatusChange
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onCreateTask,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = gradientTop),
                shape = RoundedCornerShape(cornerRadius8)
            ) {
                Text("Создать задачу")
            }
        }
    }
}

@Composable
private fun PrioritySelector(
    selectedPriority: String,
    onPrioritySelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PriorityButton(
            text = "Срочно",
            isSelected = selectedPriority == "urgent",
            onClick = { onPrioritySelected("urgent") }
        )
        PriorityButton(
            text = "Планово",
            isSelected = selectedPriority == "planned",
            onClick = { onPrioritySelected("planned") }
        )
        PriorityButton(
            text = "Доп. задача",
            isSelected = selectedPriority == "optional",
            onClick = { onPrioritySelected("optional") }
        )
    }
}

@Composable
private fun PriorityButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) gradientTop else unselectedButton
        ),
        shape = RoundedCornerShape(cornerRadius8)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else primaryText,
            style = Typography.bodySmall
        )
    }
}