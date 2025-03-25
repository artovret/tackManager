package com.titixoid.taskmanager.ui.admin.workers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.titixoid.taskmanager.R
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.background
import com.titixoid.taskmanager.ui.theme.cornerRadius8
import com.titixoid.taskmanager.ui.theme.elevation4
import com.titixoid.taskmanager.ui.theme.iconHeight32
import com.titixoid.taskmanager.ui.theme.padding16
import com.titixoid.taskmanager.ui.theme.padding32
import com.titixoid.taskmanager.ui.theme.primaryText
import com.titixoid.taskmanager.ui.theme.primaryTitleText
import com.titixoid.taskmanager.ui.theme.secondText
import com.titixoid.taskmanager.ui.theme.space16
import com.titixoid.taskmanager.ui.theme.space32
import com.titixoid.taskmanager.ui.theme.space8
import com.titixoid.taskmanager.ui.theme.verticalPadding8
import com.titixoid.taskmanager.ui.widgets.BackgroundColumn
import com.titixoid.taskmanager.ui.widgets.BottomBarInsetsSpacer
import com.titixoid.taskmanager.ui.widgets.StatusBarInsetsSpacer

@Composable
fun AdminWorkerListScreen(
    uiState: AdminWorkerListUiState,
    onWorkerClick: (String) -> Unit,
    onAddClicked: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundColumn {
            StatusBarInsetsSpacer()
            Spacer(modifier = Modifier.height(space16))
            Text(
                text = stringResource(R.string.worker_list),
                style = Typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(space16))
            Text(
                text = stringResource(R.string.have_nice_day),
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
                            .padding(vertical = verticalPadding8)
                            .shadow(
                                elevation = elevation4,
                                shape = RoundedCornerShape(cornerRadius8)
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(cornerRadius8)
                            )
                            .clickable { onWorkerClick(worker.id) }
                            .padding(padding16)
                    ) {
                        Column {
                            Text(
                                text = "${worker.firstName} ${worker.lastName}",
                                style = Typography.bodyLarge,
                                color = primaryText
                            )
                            Spacer(modifier = Modifier.height(space8))
                            Text(
                                text = stringResource(R.string.tasks_count, worker.taskCount),
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

        FloatingActionButton(
            onClick = onAddClicked,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(padding32),
            containerColor = background
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_worker),
                contentDescription = stringResource(R.string.add_worker),
                tint = primaryTitleText,
                modifier = Modifier.height(iconHeight32)
            )
        }
    }
}