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
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.titixoid.taskmanager.ui.theme.Gradients
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.cornerRadius8
import com.titixoid.taskmanager.ui.theme.gradientTop
import com.titixoid.taskmanager.ui.theme.primaryText
import com.titixoid.taskmanager.ui.theme.primaryWhite
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
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = onCancel,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Назад",
                tint = primaryWhite
            )
        }

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
                    .padding(top = 36.dp)
            ) {
                LabeledInputSection(
                    label = "Название",
                    value = uiState.title,
                    onValueChange = onTitleChange,
                    placeholder = "Введите название"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp)
                .background(
                    color = primaryWhite,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LabeledInputSection(
                label = "Описание",
                value = uiState.description,
                onValueChange = onDescriptionChange,
                placeholder = "Введите описание",
                isMultiline = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Статус",
                style = Typography.labelSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            StatusSelector(
                selectedStatus = uiState.status,
                onStatusSelected = onStatusChange
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
private fun LabeledInputSection(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isMultiline: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = Typography.labelSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (isMultiline) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = secondaryGray,
                    focusedBorderColor = gradientTop
                )
            )
        } else {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = Typography.headlineLarge.copy(color = primaryText),
                cursorBrush = SolidColor(gradientTop),
                decorationBox = { innerTextField ->
                    Column {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    style = Typography.headlineLarge,
                                    color = primaryText.copy(alpha = 0.8f)
                                )
                            }
                            innerTextField()
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(primaryText.copy(alpha = 0.3f))
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
            text = "Доп. задача",
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
        shape = RoundedCornerShape(cornerRadius8)
    ) {
        Text(
            text = text,
            color = if (isSelected) primaryWhite else primaryText,
            style = Typography.bodySmall
        )
    }
}