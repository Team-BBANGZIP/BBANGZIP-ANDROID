package org.android.bbangzip.presentation.ui.todo.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.bottomsheet.BbangZipBottomSheetSlot
import org.android.bbangzip.presentation.common.component.button.BbangZipButtonDefaults
import org.android.bbangzip.presentation.common.component.button.BbangzipBaseButton
import org.android.bbangzip.presentation.common.component.button.TimeSettingButton
import org.android.bbangzip.presentation.common.component.row.InteractionRow
import org.android.bbangzip.presentation.common.component.toggle.BbangZipSwitch
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.ui.todo.type.TodoSettingActionType
import org.android.bbangzip.ui.theme.BbangZipTheme
import java.time.LocalTime

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TodoSettingBottomSheet(
    isBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit,
    todoName: String,
    categoryName: String,
    isCompleted: Boolean,
    isNotificationEnabled: Boolean,
    onNotificationEnabledChange: () -> Unit,
    modifier: Modifier = Modifier,
    startTime: LocalTime? = null,
) {
    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = todoName,
                    style = BbangZipTheme.typography.title3SemiBold,
                    color = BbangZipTheme.color.labelNormal_6B6560,
                )

                Gap(height = 4.dp)

                Text(
                    text = categoryName,
                    style = BbangZipTheme.typography.title3SemiBold,
                    color = BbangZipTheme.color.labelAlternative_A29D96,
                )

                Gap(height = 28.dp)
            }
        },
        content = {
            Column {
                InteractionButtons()

                Gap(20.dp)

                if (isCompleted) {
                    HorizontalDivider(
                        color = BbangZipTheme.color.componentStrong_F6F6F5,
                    )

                    Gap(20.dp)

                    InteractionRow(
                        interactionIconResId = R.drawable.ic_again_default_24,
                        actionName = stringResource(R.string.todo_setting_do_again_action_name),
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        TodoSettingActionType.entries.forEachIndexed { index, actionType ->
                            InteractionRow(
                                interactionIconResId = actionType.interactionIconResId,
                                actionName = stringResource(actionType.actionName),
                                interactionButton = {
                                    when (actionType) {
                                        TodoSettingActionType.START_TIME -> {
                                            TimeSettingButton(
                                                onSettingTimeClick = {},
                                                startTime = startTime,
                                            )
                                        }

                                        TodoSettingActionType.NOTIFICATION -> {
                                            BbangZipSwitch(
                                                modifier = Modifier.fillMaxWidth(44 / 335f),
                                                isChecked = isNotificationEnabled,
                                                onCheckedChange = onNotificationEnabledChange,
                                            )
                                        }

                                        else -> {}
                                    }
                                },
                            )

                            if (index == 1) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    color = BbangZipTheme.color.componentStrong_F6F6F5,
                                )
                            }
                        }
                    }
                }

                Gap(height = 28.dp)
            }
        },
    )
}

@Composable
private fun InteractionButtons(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        BbangzipBaseButton(
            modifier = Modifier.weight(1f),
            onClick = {},
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_return_thin_24),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = BbangZipTheme.color.staticWhite_FFFFFF,
                )
            },
            content = {
                Text(
                    text = stringResource(R.string.button_label_edit),
                    style = BbangZipTheme.typography.body2Medium,
                    color = BbangZipTheme.color.staticWhite_FFFFFF,
                )
            },
            colors =
                BbangZipButtonDefaults.colors(
                    enabledContainerColor = BbangZipTheme.color.primaryLight_C8B5A2,
                ),
        )

        Gap(width = 8.dp)

        BbangzipBaseButton(
            modifier = Modifier.weight(1f),
            onClick = {},
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_trash_default_24),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = BbangZipTheme.color.staticWhite_FFFFFF,
                )
            },
            colors =
                BbangZipButtonDefaults.colors(
                    enabledContainerColor = BbangZipTheme.color.primaryNormal_897869,
                ),
            content = {
                Text(
                    text = stringResource(R.string.button_label_delete),
                    style = BbangZipTheme.typography.body2Medium,
                    color = BbangZipTheme.color.staticWhite_FFFFFF,
                )
            },
        )
    }
}


@Preview
@Composable
fun TodoSettingBottomSheetPreview() {
    var isBottomSheetVisible by remember { mutableStateOf(true) }
    var isNotificationEnabled by remember { mutableStateOf(false) }

    TodoSettingBottomSheet(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = { isBottomSheetVisible = !isBottomSheetVisible },
        todoName = "미완료 바텀시트",
        categoryName = "바텀시트",
        isCompleted = false,
        isNotificationEnabled = isNotificationEnabled,
        onNotificationEnabledChange = { isNotificationEnabled = !isNotificationEnabled },
    )

    TodoSettingBottomSheet(
        isBottomSheetVisible = !isBottomSheetVisible,
        onDismissRequest = { isBottomSheetVisible = !isBottomSheetVisible },
        todoName = "완료 바텀시트",
        categoryName = "바텀시트",
        isCompleted = true,
        isNotificationEnabled = isNotificationEnabled,
        onNotificationEnabledChange = { isNotificationEnabled = !isNotificationEnabled },
    )
}
