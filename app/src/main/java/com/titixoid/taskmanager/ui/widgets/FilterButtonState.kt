package com.titixoid.taskmanager.ui.widgets


import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Immutable
data class FilterButtonState(
    val filter: TaskFilter,
    val backgroundColor: Color,
    val textColor: Color,
    val textStyle: TextStyle
)