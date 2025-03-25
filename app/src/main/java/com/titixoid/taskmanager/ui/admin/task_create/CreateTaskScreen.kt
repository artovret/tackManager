package com.titixoid.taskmanager.ui.admin.task_create

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.titixoid.taskmanager.R
import com.titixoid.taskmanager.ui.theme.Gradients
import com.titixoid.taskmanager.ui.theme.TaskManagerTheme
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.borderGray
import com.titixoid.taskmanager.ui.theme.cornerRadius100
import com.titixoid.taskmanager.ui.theme.cornerRadius30
import com.titixoid.taskmanager.ui.theme.cornerRadius50
import com.titixoid.taskmanager.ui.theme.cornerRadiusPercent50
import com.titixoid.taskmanager.ui.theme.descriptionText
import com.titixoid.taskmanager.ui.theme.gradientTop
import com.titixoid.taskmanager.ui.theme.inputFieldHeight200
import com.titixoid.taskmanager.ui.theme.itemHeight70
import com.titixoid.taskmanager.ui.theme.negativeOffsetX200
import com.titixoid.taskmanager.ui.theme.negativeOffsetY180
import com.titixoid.taskmanager.ui.theme.offsetX180
import com.titixoid.taskmanager.ui.theme.offsetY130
import com.titixoid.taskmanager.ui.theme.padding16
import com.titixoid.taskmanager.ui.theme.padding20
import com.titixoid.taskmanager.ui.theme.padding30
import com.titixoid.taskmanager.ui.theme.padding300
import com.titixoid.taskmanager.ui.theme.padding56
import com.titixoid.taskmanager.ui.theme.padding8
import com.titixoid.taskmanager.ui.theme.primaryText
import com.titixoid.taskmanager.ui.theme.primaryWhite
import com.titixoid.taskmanager.ui.theme.secondTitleText
import com.titixoid.taskmanager.ui.theme.secondaryGray
import com.titixoid.taskmanager.ui.theme.size400
import com.titixoid.taskmanager.ui.theme.space16
import com.titixoid.taskmanager.ui.theme.space24
import com.titixoid.taskmanager.ui.theme.space8
import com.titixoid.taskmanager.ui.theme.taskNameText
import com.titixoid.taskmanager.ui.theme.unselectedButton
import com.titixoid.taskmanager.ui.theme.zeroValue
import com.titixoid.taskmanager.ui.widgets.StyledInputField

@Composable
fun CreateTaskScreen(
    uiState: CreateTaskUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onStatusChange: (String) -> Unit,
    onCreateTask: () -> Unit
) {
    val currentContext = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(size400)
                .background(Gradients.MainGradient)
        ) {
            Box(
                modifier = Modifier
                    .size(size400)
                    .align(Alignment.TopStart)
                    .offset(x = negativeOffsetX200, y = offsetY130)
                    .background(
                        color = gradientTop.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(cornerRadius100)
                    )
            )

            Box(
                modifier = Modifier
                    .size(size400)
                    .align(Alignment.TopEnd)
                    .offset(x = offsetX180, y = negativeOffsetY180)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(cornerRadius100)
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding16)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.create_task_title),
                    style = Typography.titleSmall.copy(color = primaryWhite)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = padding20)
                    .padding(top = padding56)
            ) {
                StyledInputField(
                    label = stringResource(R.string.task_name),
                    value = uiState.title,
                    onValueChange = onTitleChange,
                    placeholder = stringResource(R.string.enter_task_name),
                    labelColor = primaryWhite.copy(alpha = 0.6f),
                    textColor = taskNameText,
                    cursorColor = descriptionText,
                    placeholderColor = primaryWhite.copy(alpha = 0.3f),
                    separatorColor = primaryWhite.copy(alpha = 0.9f)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding300)
                .background(
                    color = primaryWhite,
                    shape = RoundedCornerShape(topStart = cornerRadius30, topEnd = cornerRadius30)
                )
                .padding(padding30)
        ) {
            Spacer(modifier = Modifier.height(space16))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.task_description),
                    style = Typography.labelSmall,
                    color = secondTitleText.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = padding8)
                )

                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = onDescriptionChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(inputFieldHeight200),
                    textStyle = Typography.headlineLarge.copy(color = descriptionText),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = secondaryGray,
                        focusedBorderColor = borderGray
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            StatusSelector(
                selectedStatus = uiState.status,
                onStatusSelected = onStatusChange
            )

            Spacer(modifier = Modifier.height(space24))

            Button(
                onClick = {
                    if (uiState.isFormValid && !uiState.isLoading) {
                        onCreateTask()
                    } else {
                        Toast.makeText(currentContext, "Заполните все поля", Toast.LENGTH_SHORT)
                            .show()
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(itemHeight70),
                shape = RoundedCornerShape(percent = cornerRadiusPercent50),
                contentPadding = PaddingValues(zeroValue),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Gradients.MainGradient,
                            shape = RoundedCornerShape(percent = cornerRadiusPercent50)
                        )
                        .fillMaxWidth()
                        .padding(padding8),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.create_task_button),
                        style = Typography.bodyLarge,
                        color = primaryWhite
                    )
                }
            }
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
        horizontalArrangement = Arrangement.spacedBy(space8)
    ) {
        StatusButton(
            text = stringResource(R.string.status_urgent),
            isSelected = selectedStatus == "urgent",
            onClick = { onStatusSelected("urgent") }
        )
        StatusButton(
            text = stringResource(R.string.status_planned),
            isSelected = selectedStatus == "planned",
            onClick = { onStatusSelected("planned") }
        )
        StatusButton(
            text = stringResource(R.string.status_optional),
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
        shape = RoundedCornerShape(cornerRadius50)
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
            onCreateTask = {}
        )
    }
}