package com.titixoid.taskmanager.ui.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.titixoid.taskmanager.R
import com.titixoid.taskmanager.ui.theme.Gradients
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.cornerRadiusPercent50
import com.titixoid.taskmanager.ui.theme.itemHeight90
import com.titixoid.taskmanager.ui.theme.itemWidth200
import com.titixoid.taskmanager.ui.theme.padding34
import com.titixoid.taskmanager.ui.theme.padding8
import com.titixoid.taskmanager.ui.theme.zeroValue

@Composable
fun StartScreen(
    onManualStartClicked: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding34)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = stringResource(R.string.app_logo)
                )
            }
            if (onManualStartClicked != null) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { onManualStartClicked() },
                        modifier = Modifier
                            .width(itemWidth200)
                            .height(itemHeight90),
                        shape = RoundedCornerShape(percent = cornerRadiusPercent50),
                        contentPadding = PaddingValues(zeroValue),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = Gradients.MainGradient,
                                    shape = RoundedCornerShape(percent = cornerRadiusPercent50)
                                )
                                .width(itemWidth200)
                                .padding(padding8),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.enter),
                                style = Typography.bodySmall,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                }
            }
        }
    }
}