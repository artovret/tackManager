package com.titixoid.taskmanager.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.titixoid.taskmanager.R


val RobotoFontFamily = FontFamily(
    Font(
        resId = R.font.poppins_semi_bold,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal
    ),

    Font(
        resId = R.font.poppins_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    )
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 46.17.sp,
        color = primaryTitleText
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 23.09.sp,
        color = secondTitleText
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.42.sp,
        color = primaryText
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = primaryText
    ),
    labelSmall = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = primaryText
    ),
    bodySmall = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.48.sp,
        color = secondText
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
    )
)