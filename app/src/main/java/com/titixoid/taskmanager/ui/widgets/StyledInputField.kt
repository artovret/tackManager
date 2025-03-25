package com.titixoid.taskmanager.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.padding8
import com.titixoid.taskmanager.ui.theme.separatorHeight1

@Composable
fun StyledInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    labelColor: Color,
    textColor: Color,
    cursorColor: Color,
    placeholderColor: Color,
    separatorColor: Color
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = Typography.labelSmall,
            color = labelColor,
            modifier = Modifier.padding(bottom = padding8)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.headlineLarge.copy(color = textColor),
            cursorBrush = SolidColor(cursorColor),
            decorationBox = { innerTextField ->
                Column {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = Typography.headlineLarge,
                                color = placeholderColor
                            )
                        }
                        innerTextField()
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(separatorHeight1)
                            .background(separatorColor)
                    )
                }
            }
        )
    }
}