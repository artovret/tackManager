package com.titixoid.taskmanager.ui.admin.worker_create

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.titixoid.taskmanager.ui.theme.cornerRadius100
import com.titixoid.taskmanager.ui.theme.cornerRadius30
import com.titixoid.taskmanager.ui.theme.cornerRadiusPercent50
import com.titixoid.taskmanager.ui.theme.descriptionText
import com.titixoid.taskmanager.ui.theme.gradientTop
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
import com.titixoid.taskmanager.ui.theme.primaryWhite
import com.titixoid.taskmanager.ui.theme.secondTitleText
import com.titixoid.taskmanager.ui.theme.size400
import com.titixoid.taskmanager.ui.theme.taskNameText
import com.titixoid.taskmanager.ui.theme.zeroValue
import com.titixoid.taskmanager.ui.widgets.StyledInputField


@Composable
fun CreateWorkerScreen(
    uiState: CreateWorkerUiState,
    onLoginChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onCreateWorker: () -> Unit
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
                    text = stringResource(R.string.create_worker_title),
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
                    label = stringResource(R.string.worker_login_title),
                    value = uiState.login,
                    onValueChange = onLoginChange,
                    placeholder = stringResource(R.string.enter_login),
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

            Spacer(modifier = Modifier.weight(0.7f))

            StyledInputField(
                label = stringResource(R.string.worker_first_name),
                value = uiState.firstName,
                onValueChange = onFirstNameChange,
                placeholder = stringResource(R.string.enter_first_name),
                labelColor = secondTitleText.copy(alpha = 0.6f),
                textColor = descriptionText,
                cursorColor = descriptionText,
                placeholderColor = secondTitleText.copy(alpha = 0.3f),
                separatorColor = secondTitleText.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.weight(0.7f))

            StyledInputField(
                label = stringResource(R.string.worker_last_name),
                value = uiState.lastName,
                onValueChange = onLastNameChange,
                placeholder = stringResource(R.string.enter_last_name),
                labelColor = secondTitleText.copy(alpha = 0.6f),
                textColor = descriptionText,
                cursorColor = descriptionText,
                placeholderColor = secondTitleText.copy(alpha = 0.3f),
                separatorColor = secondTitleText.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.weight(0.7f))

            StyledInputField(
                label = stringResource(R.string.worker_password),
                value = uiState.password,
                onValueChange = onPasswordChange,
                placeholder = stringResource(R.string.enter_password),
                labelColor = secondTitleText.copy(alpha = 0.6f),
                textColor = descriptionText,
                cursorColor = descriptionText,
                placeholderColor = secondTitleText.copy(alpha = 0.3f),
                separatorColor = secondTitleText.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (uiState.isFormValid && !uiState.isLoading) {
                        onCreateWorker()
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
                )
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
                        text = stringResource(R.string.create_worker_button),
                        style = Typography.bodyLarge,
                        color = primaryWhite
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_6_pro")
@Composable
fun CreateWorkerPreview() {
    TaskManagerTheme {
        CreateWorkerScreen(
            uiState = CreateWorkerUiState(firstName = "Иван", lastName = "Иванов"),
            onFirstNameChange = {},
            onLastNameChange = {},
            onCreateWorker = {},
            onLoginChange = {},
            onPasswordChange = {}
        )
    }
}