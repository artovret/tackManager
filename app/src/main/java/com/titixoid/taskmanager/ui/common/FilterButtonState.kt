package com.titixoid.taskmanager.ui.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class FilterButtonState(
    val filter: String,
    val displayName: String,
    val backgroundColor: Color,
    val textColor: Color,
    val textStyle: TextStyle
)
