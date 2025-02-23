package com.titixoid.taskmanager.ui.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.titixoid.taskmanager.ui.theme.TaskManagerTheme
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.cursorColor
import com.titixoid.taskmanager.ui.theme.descriptionText
import com.titixoid.taskmanager.ui.theme.primaryWhite

@Composable
fun FormField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    hasError: Boolean = false
) {
    TextField(
        value = value,
        shape = RoundedCornerShape(8.dp),
        textStyle = Typography.bodySmall,
        modifier =
        modifier
            .height(56.dp)
            .border(
                width = 1.dp,
                color = if (hasError) MaterialTheme.colorScheme.error
                else descriptionText,
                shape = RoundedCornerShape(8.dp)
            ),
        visualTransformation = visualTransformation,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = primaryWhite,
            unfocusedContainerColor = primaryWhite,
            cursorColor = cursorColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
    )
}


@Preview(showBackground = true)
@Composable
fun FormFieldPreview() {
    TaskManagerTheme {
        FormField(
            value = "test@test.ru",
            onValueChange = {},
            hasError = true
        )
    }
}