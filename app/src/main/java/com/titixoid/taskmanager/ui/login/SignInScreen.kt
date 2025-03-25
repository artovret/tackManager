package com.titixoid.taskmanager.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.titixoid.taskmanager.R
import com.titixoid.taskmanager.ui.theme.Gradients
import com.titixoid.taskmanager.ui.theme.TaskManagerTheme
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.cornerRadiusPercent50
import com.titixoid.taskmanager.ui.theme.itemHeight70
import com.titixoid.taskmanager.ui.theme.itemWidth300
import com.titixoid.taskmanager.ui.theme.padding8
import com.titixoid.taskmanager.ui.theme.primaryWhite
import com.titixoid.taskmanager.ui.theme.space16
import com.titixoid.taskmanager.ui.theme.space32
import com.titixoid.taskmanager.ui.theme.space40
import com.titixoid.taskmanager.ui.theme.space8
import com.titixoid.taskmanager.ui.theme.space90
import com.titixoid.taskmanager.ui.theme.zeroValue
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
            text = stringResource(R.string.login_title),
            style = Typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(space40))


        Text(
            text = stringResource(R.string.login_label),
            style = Typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(space8))

        FormField(
            modifier = Modifier.fillMaxWidth(),
            value = signInUiState.login,
            hasError = signInUiState.error,
            onValueChange = onEmailChanged
        )
        Spacer(modifier = Modifier.height(space40))

        Text(
            text = stringResource(R.string.password_label),
            style = Typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(space8))

        FormField(
            modifier = Modifier.fillMaxWidth(),
            value = signInUiState.password,
            hasError = signInUiState.error,
            onValueChange = onPasswordChanged,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(space90))

        Button(
            onClick = onLoginClicked,
            modifier = Modifier
                .width(itemWidth300)
                .height(itemHeight70),
            shape = RoundedCornerShape(percent = cornerRadiusPercent50),
            contentPadding = PaddingValues(zeroValue),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            enabled = !signInUiState.isLoading
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Gradients.MainGradient,
                        shape = RoundedCornerShape(percent = cornerRadiusPercent50)
                    )
                    .width(itemWidth300)
                    .padding(padding8),
                contentAlignment = Alignment.Center
            ) {
                if (signInUiState.isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.background)
                } else {
                    Text(
                        text = stringResource(R.string.enter),
                        style = Typography.bodyLarge,
                        color = primaryWhite
                    )
                }
            }
        }


        if (signInUiState.error) {
            Text(
                text = stringResource(R.string.login_error),
                style = Typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = space8),
                textAlign = TextAlign.Center
            )
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
                login = "traci.knox@example.com",
                password = "ad"
            ),
            onEmailChanged = {},
            onPasswordChanged = {},
            onLoginClicked = {}
        )
    }
}