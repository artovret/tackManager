package com.titixoid.taskmanager.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.titixoid.taskmanager.ui.theme.Gradients
import com.titixoid.taskmanager.ui.theme.TaskManagerTheme
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.space16
import com.titixoid.taskmanager.ui.theme.space32
import com.titixoid.taskmanager.ui.theme.space40
import com.titixoid.taskmanager.ui.theme.space8
import com.titixoid.taskmanager.ui.theme.space90
import com.titixoid.taskmanager.ui.widgets.BackgroundColumn
import com.titixoid.taskmanager.ui.widgets.BottomBarInsetsSpacer
import com.titixoid.taskmanager.ui.widgets.FormField
import com.titixoid.taskmanager.ui.widgets.StatusBarInsetsSpacer


@Composable
fun SignInScreen(
    signInUiState: SignInUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
) {
    BackgroundColumn {
        StatusBarInsetsSpacer()
        Spacer(modifier = Modifier.height(space16))
        Spacer(modifier = Modifier.height(space32))

        Text(
            text = "Вход",
            style = Typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(space40))

        Text(
            text = "Логин",
            style = Typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(space8))

        FormField(
            modifier = Modifier.fillMaxWidth(),
            value = signInUiState.email,
            hasError = false, // TODO брать из uiState
            onValueChange = onEmailChanged // TODO добавить обработчик
        )
        Spacer(modifier = Modifier.height(space40))

        Text(
            text = "Пароль",
            style = Typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(space8))

        FormField(
            modifier = Modifier.fillMaxWidth(),
            value = signInUiState.password, // TODO заменить на значение из uiState
            onValueChange = onPasswordChanged, // TODO добавить обработчик
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(space90))

        Button(
            onClick = onLoginClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            shape = RoundedCornerShape(percent = 50),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Gradients.MainGradient,
                        shape = RoundedCornerShape(percent = 50)
                    )
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Войти",
                    style = Typography.bodyLarge,
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
        BottomBarInsetsSpacer()
    }
}


@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    TaskManagerTheme {
        SignInScreen(
            signInUiState = SignInUiState(
                email = "traci.knox@example.com",
                password = "ad"
        ),
            onEmailChanged = {},
            onPasswordChanged = {},
            onLoginClicked = {}
        )
    }
}