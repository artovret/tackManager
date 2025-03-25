package com.titixoid.taskmanager.ui.admin.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.titixoid.domain.models.Task
import com.titixoid.taskmanager.R
import com.titixoid.taskmanager.ui.common.TaskFilterEnum
import com.titixoid.taskmanager.ui.theme.Typography
import com.titixoid.taskmanager.ui.theme.background
import com.titixoid.taskmanager.ui.theme.cornerRadius50
import com.titixoid.taskmanager.ui.theme.cornerRadius8
import com.titixoid.taskmanager.ui.theme.elevation4
import com.titixoid.taskmanager.ui.theme.iconHeight20
import com.titixoid.taskmanager.ui.theme.iconHeight32
import com.titixoid.taskmanager.ui.theme.itemHeight50
import com.titixoid.taskmanager.ui.theme.padding32
import com.titixoid.taskmanager.ui.theme.padding8
import com.titixoid.taskmanager.ui.theme.primaryText
import com.titixoid.taskmanager.ui.theme.primaryTitleText
import com.titixoid.taskmanager.ui.theme.primaryWhite
import com.titixoid.taskmanager.ui.theme.secondText
import com.titixoid.taskmanager.ui.theme.space16
import com.titixoid.taskmanager.ui.theme.space32
import com.titixoid.taskmanager.ui.theme.space4
import com.titixoid.taskmanager.ui.theme.verticalPadding8
import com.titixoid.taskmanager.ui.theme.zeroValue
import com.titixoid.taskmanager.ui.widgets.BackgroundColumn
import com.titixoid.taskmanager.ui.widgets.BottomBarInsetsSpacer
import com.titixoid.taskmanager.ui.widgets.StatusBarInsetsSpacer


@Composable
fun AdminTaskListScreen(
    uiState: AdminTaskListUiState,
    onFilterSelected: (TaskFilterEnum) -> Unit,
    onAddClicked: () -> Unit,
    onDeleteClicked: (Task) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundColumn {
            StatusBarInsetsSpacer()
            Spacer(modifier = Modifier.height(space16))

            Text(
                text = stringResource(R.string.task_list),
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

            Row(modifier = Modifier.fillMaxWidth()) {
                uiState.filterButtonStates.forEachIndexed { index, buttonState ->
                    Button(
                        onClick = { onFilterSelected(buttonState.filter) },
                        modifier = Modifier
                            .weight(1f)
                            .height(itemHeight50),
                        shape = RoundedCornerShape(cornerRadius50),
                        contentPadding = PaddingValues(zeroValue),
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(
                                    elevation = elevation4,
                                    shape = RoundedCornerShape(cornerRadius50)
                                )
                                .background(
                                    SolidColor(buttonState.backgroundColor),
                                    shape = RoundedCornerShape(cornerRadius50)
                                )
                                .padding(padding8),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = buttonState.filter.displayName,
                                style = buttonState.textStyle,
                                color = buttonState.textColor,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    if (index < uiState.filterButtonStates.size - 1) {
                        Spacer(modifier = Modifier.width(space16))
                    }
                }
            }
            Spacer(modifier = Modifier.height(space32))

            Column(modifier = Modifier.fillMaxWidth()) {
                if (uiState.filteredTasks.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_tasks),
                        style = Typography.bodySmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                } else {
                    uiState.filteredTasks.forEach { task ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = verticalPadding8)
                                .shadow(
                                    elevation = elevation4,
                                    shape = RoundedCornerShape(cornerRadius8)
                                )
                                .background(
                                    color = primaryWhite,
                                    shape = RoundedCornerShape(cornerRadius8)
                                )
                                .clickable {}
                                .padding(padding8)
                        ) {
                            Column(modifier = Modifier.padding(padding8)) {
                                Text(
                                    text = task.title,
                                    style = Typography.bodyLarge,
                                    color = primaryText
                                )
                                Spacer(modifier = Modifier.height(space4))
                                Text(
                                    text = task.description,
                                    style = Typography.bodySmall,
                                    color = secondText
                                )
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = stringResource(R.string.delete_task),
                                tint = secondText,
                                modifier = Modifier
                                    .height(iconHeight20)
                                    .align(Alignment.TopEnd)
                                    .clickable { onDeleteClicked(task) }
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
                painter = painterResource(id = R.drawable.ic_add_task),
                contentDescription = stringResource(R.string.add_task),
                tint = primaryTitleText,
                modifier = Modifier.height(iconHeight32)
            )
        }
    }
}