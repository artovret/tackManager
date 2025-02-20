package com.titixoid.taskmanager.ui.admin.workers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.primaryText
import com.titixoid.taskmanager.ui.theme.secondText
import com.titixoid.taskmanager.ui.theme.space16
import com.titixoid.taskmanager.ui.theme.space32
import com.titixoid.taskmanager.ui.widgets.BackgroundColumn
import com.titixoid.taskmanager.ui.widgets.BottomBarInsetsSpacer
import com.titixoid.taskmanager.ui.widgets.StatusBarInsetsSpacer

@Composable
fun AdminWorkerListScreen(
    uiState: AdminWorkerListUiState,
    onWorkerClick: (String) -> Unit,
) {
    BackgroundColumn {
        StatusBarInsetsSpacer()
        Spacer(modifier = Modifier.height(space16))
        
        Text(
            text = "Список работников",
            style = Typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(space16))
        Text(
            text = "Приятного дня",
            style = Typography.bodySmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(space16))
        
        Column(modifier = Modifier.fillMaxWidth()) {
            uiState.workers.forEach { worker ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onWorkerClick(worker.id) }
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "${worker.firstName} ${worker.lastName}",
                            style = Typography.bodyLarge,
                            color = primaryText
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Задач: ${worker.taskCount}",
                            style = Typography.bodySmall,
                            color = secondText
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(space32))
        BottomBarInsetsSpacer()
    }
} 