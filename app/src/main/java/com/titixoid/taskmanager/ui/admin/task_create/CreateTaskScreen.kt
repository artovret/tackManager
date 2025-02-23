package com.titixoid.taskmanager.ui.admin.task_create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.titixoid.taskmanager.ui.theme.Gradients
import com.titixoid.taskmanager.ui.theme.TaskManagerTheme
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.borderGray
import com.titixoid.taskmanager.ui.theme.descriptionText
import com.titixoid.taskmanager.ui.theme.gradientTop
import com.titixoid.taskmanager.ui.theme.primaryText
import com.titixoid.taskmanager.ui.theme.primaryWhite
import com.titixoid.taskmanager.ui.theme.secondTitleText
import com.titixoid.taskmanager.ui.theme.secondaryGray
import com.titixoid.taskmanager.ui.theme.taskNameText
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
    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Gradients.MainGradient)
        ) {
            Box(
                modifier = Modifier
                    .size(400.dp)
                    .align(Alignment.TopStart)
                    .offset(x = (-200).dp, y = 130.dp)
                    .background(
                        color = gradientTop.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(100)
                    )
            )

            Box(
                modifier = Modifier
                    .size(400.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 180.dp, y = (-180).dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(100)
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Создание задачи",
                    style = Typography.titleSmall.copy(color = primaryWhite)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 56.dp)
            ) {
                LabeledInputSection(
                    label = "Название",
                    textColor = primaryWhite,
                    value = uiState.title,
                    onValueChange = onTitleChange,
                    placeholder = "Введите название"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 300.dp)
                .background(
                    color = primaryWhite,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .padding(30.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LabeledInputSection(
                label = "Описание",
                textColor = secondTitleText,
                value = uiState.description,
                onValueChange = onDescriptionChange,
                placeholder = "Введите описание",
                isMultiline = true
            )

            Spacer(modifier = Modifier.weight(1f))

            StatusSelector(
                selectedStatus = uiState.status,
                onStatusSelected = onStatusChange
            )

            Spacer(modifier = Modifier.height(24.dp))


            Button(
                onClick = onCreateTask,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                shape = RoundedCornerShape(percent = 50),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Gradients.MainGradient,
                            shape = RoundedCornerShape(percent = 50)
                        )
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                            text = "Создать задачу",
                            style = Typography.bodyLarge,
                        color = primaryWhite
                        )
                }
            }
        }
    }
}

@Composable
private fun LabeledInputSection(
    label: String,
    textColor: Color,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isMultiline: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = Typography.labelSmall,
            color = textColor.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (isMultiline) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                textStyle = Typography.headlineLarge.copy(color = descriptionText),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = secondaryGray,
                    focusedBorderColor = borderGray
                )
            )
        } else {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = Typography.headlineLarge.copy(color = taskNameText),
                cursorBrush = SolidColor(descriptionText),
                decorationBox = { innerTextField ->
                    Column {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    style = Typography.headlineLarge,
                                    color = textColor.copy(alpha = 0.3f)
                                )
                            }
                            innerTextField()
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(primaryWhite.copy(alpha = 0.9f))
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun StatusSelector(
    selectedStatus: String,
    onStatusSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatusButton(
            text = "Срочно",
            isSelected = selectedStatus == "urgent",
            onClick = { onStatusSelected("urgent") }
        )
        StatusButton(
            text = "Планово",
            isSelected = selectedStatus == "planned",
            onClick = { onStatusSelected("planned") }
        )
        StatusButton(
            text = "Прочее",
            isSelected = selectedStatus == "optional",
            onClick = { onStatusSelected("optional") }
        )
    }
}

@Composable
private fun StatusButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) gradientTop else unselectedButton
        ),
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) primaryWhite else primaryText,
            style = Typography.bodySmall
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_6_pro")
@Composable
fun CreateTaskPreview() {
    TaskManagerTheme {
        CreateTaskScreen(
            uiState = CreateTaskUiState(
                title = "Новая задача",
                description = "Описание тестовой задачи",
                status = "planned"
            ),
            onTitleChange = {},
            onDescriptionChange = {},
            onStatusChange = {},
            onCreateTask = {},
            onCancel = {}
        )
    }
}
