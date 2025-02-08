package com.titixoid.taskmanager.ui.widgets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.titixoid.taskmanager.R
import com.titixoid.taskmanager.ui.theme.TaskManagerTheme
import com.titixoid.taskmanager.ui.theme.itemHeight24
import com.titixoid.taskmanager.ui.theme.itemWidth24
import androidx.compose.ui.graphics.Color
import com.titixoid.taskmanager.ui.theme.Typography

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
                       else MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            ),
        visualTransformation = visualTransformation,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
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