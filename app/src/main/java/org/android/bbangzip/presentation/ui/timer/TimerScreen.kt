package org.android.bbangzip.presentation.ui.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.toggle.BbangZipSegmentedButton
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.common.util.extension.dropShadow
import org.android.bbangzip.presentation.common.util.extension.innerShadow
import org.android.bbangzip.presentation.common.util.extension.noRippleClickable
import org.android.bbangzip.presentation.ui.shared.SharedContract
import org.android.bbangzip.presentation.ui.timer.component.bottomsheet.BreadSelectBottomSheet
import org.android.bbangzip.presentation.ui.timer.component.bottomsheet.CompleteBottomSheet
import org.android.bbangzip.presentation.ui.timer.component.bottomsheet.ResetBottomSheet
import org.android.bbangzip.presentation.ui.timer.component.bottomsheet.RestartBottomSheet
import org.android.bbangzip.presentation.ui.timer.component.maintimer.TimerDisplay
import org.android.bbangzip.presentation.ui.timer.contract.TimerContract
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerSessionUiState
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun TimerScreen(
    timerState: TimerContract.TimerState,
    sharedState: SharedContract.SharedState,
    modifier: Modifier = Modifier,
    onBreadIconClick: () -> Unit = {},
    onRestartSheetApproveBtnClick: () -> Unit = {},
    onRestartSheetDismissBtnClick: () -> Unit = {},
    onRestartBtnClick: () -> Unit = {},
    onStartBtnClick: () -> Unit = {},
    onStopBtnClick: () -> Unit = {},
    onTimeOptionToggleClick: (timeOption: Int) -> Unit = {},
    onBreadSelectionSheetClick: (breadId: Int) -> Unit = {},
    onBreadSelectionSheetDismissRequest: () -> Unit = {},
    onCompleteSheetCheckBtnClick: () -> Unit = {},
    onCompleteSheetRestartBtnClick: () -> Unit = {},
    onCompleteSheetDismissRequest: () -> Unit = {},
    onResetBtnClick: () -> Unit = {},
    onResetSheetApproveBtnClick: () -> Unit = {},
    onResetSheetDismissBtnClick: () -> Unit = {},
) {
    val isReady = timerState.timerSessionState is TimerSessionUiState.Ready
    val isRunning = timerState.timerSessionState is TimerSessionUiState.Running

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(BbangZipTheme.brush.backgroundAccentGradient)
                .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Gap(17.dp)

        TimerTopBar(
            breadCount = timerState.todayBreadCount,
        )

        Gap(50.dp)

        TimerDisplay(
            timerState = timerState,
            sharedState = sharedState,
            onBreadIconClick = onBreadIconClick,
        )

        Gap(24.dp)

        BbangZipSegmentedButton(
            options = listOf("30분", "60분"),
            indexOfSelectedOption = timerState.timerOption.timeOptionIndex,
            onOptionSelect = { index -> onTimeOptionToggleClick(index) },
            modifier =
                Modifier
                    .fillMaxWidth(0.25f)
                    .alpha(
                        if (!isReady) 0f else 1f,
                    ),
            enabled = isReady
        )

        Gap(57.dp)

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 84.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Reset 버튼
            if (!isReady) {
                SubTimerButton(
                    icon = R.drawable.ic_return_default_24,
                    contentDescription = "Restart Button",
                    onClick = onRestartBtnClick,
                )
            }

            Gap(16.dp)
            // Start/Stop 버튼
            MainTimerButton(
                isRunning = isRunning,
                onStartClick = onStartBtnClick,
                onStopClick = onStopBtnClick,
            )

            Gap(16.dp)
            // Reset 버튼
            if (!isReady) {
                SubTimerButton(
                    icon = R.drawable.ic_stop_default_24,
                    contentDescription = "Reset Button",
                    onClick = onResetBtnClick,
                )
            }
        }

        BreadSelectBottomSheet(
            currentBreadId = sharedState.breadId,
            breadList = if (isReady) timerState.timerSessionState.breadList else emptyList(),
            isBottomSheetVisible = timerState.bottomSheetState.breadSelection,
            breadCount = timerState.todayBreadCount,
            onDismissRequest = { onBreadSelectionSheetDismissRequest() },
            onBreadSelect = { breadId -> onBreadSelectionSheetClick(breadId) },
        )

        ResetBottomSheet(
            isBottomSheetVisible = timerState.bottomSheetState.reset,
            remainingTime = timerState.remainingTime,
            onReturnBtnClick = { onResetSheetDismissBtnClick() },
            onResetBtnClick = { onResetSheetApproveBtnClick() },
            onDismissRequest = { onResetSheetDismissBtnClick() },
            timeOptionIndex = timerState.timerOption.timeOptionIndex,
        )

        RestartBottomSheet(
            iisBottomSheetVisible = timerState.bottomSheetState.restart,
            onReturnBtnClick = { onRestartSheetDismissBtnClick() },
            onRestartBtnClick = { onRestartSheetApproveBtnClick() },
            onDismissRequest = { onRestartSheetDismissBtnClick() },
        )

        CompleteBottomSheet(
            iisBottomSheetVisible = timerState.bottomSheetState.complete,
            onRestartBtnClick = { onCompleteSheetRestartBtnClick() },
            onCheckTodoBtnClick = { onCompleteSheetCheckBtnClick() },
            onDismissRequest = { onCompleteSheetDismissRequest() },
            timeOptionIndex = timerState.timerOption.timeOptionIndex,
        )
    }
}

@Composable
fun TimerTopBar(
    breadCount: Int,
    modifier: Modifier = Modifier,
    onBreadCountClick: (() -> Unit) = { },
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
    ) {
        Gap()

        BreadCounter(
            count = breadCount,
            onClick = onBreadCountClick,
        )
    }
}

@Composable
fun BreadCounter(
    count: Int,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit) = { },
) {
    Row(
        modifier =
            modifier
                .border(
                    width = 1.dp,
                    color = BbangZipTheme.color.primaryLight_C8B5A2,
                    shape = RoundedCornerShape(14.dp),
                )
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .noRippleClickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_bread_default_24),
            contentDescription = "Bread Icon",
            tint = BbangZipTheme.color.primaryLight_C8B5A2,
        )

        Gap(6.dp)

        Text(
            text = count.toString(),
            style = BbangZipTheme.typography.label1SemiBold,
            color = BbangZipTheme.color.primaryNormal_897869,
        )
    }
}

@Composable
fun TimerButton(
    icon: Int,
    style: TimerButtonStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    enabled: Boolean = true,
) {
    Box(
        modifier =
            modifier
                .background(
                    color = style.backgroundColor,
                    shape = CircleShape,
                )
                .size(style.size)
                .run {
                    if (style.borderWidth > 0) {
                        border(
                            width = style.borderWidth.dp,
                            color = style.borderColor,
                            shape = CircleShape,
                        )
                    } else {
                        this
                    }
                }
                .noRippleClickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = contentDescription,
            tint = if (enabled) style.iconTint else style.iconTint.copy(alpha = 0.5f),
        )
    }
}

@Composable
fun MainTimerButton(
    isRunning: Boolean,
    onStartClick: () -> Unit,
    onStopClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (isRunning) {
        TimerButton(
            icon = R.drawable.ic_pause_default_80,
            contentDescription = "Stop Button",
            style =
                TimerButtonStyle(
                    size = 80.dp,
                    backgroundColor = BbangZipTheme.color.secondaryStrong_F2EAE4,
                    iconTint = BbangZipTheme.color.primaryNormal_897869,
                ),
            onClick = onStopClick,
            modifier =
                modifier
                    .innerShadow(
                        shape = CircleShape,
                        color = BbangZipTheme.color.primaryStrong_4B4137.copy(BbangZipTheme.opacity.opacity20),
                        blur = 1.dp,
                        offsetX = 0.dp,
                        offsetY = 1.dp,
                        spread = 0.dp,
                    )
                    .innerShadow(
                        shape = CircleShape,
                        color = BbangZipTheme.color.primaryStrong_4B4137.copy(BbangZipTheme.opacity.opacity20),
                        blur = 10.dp,
                        offsetX = 0.dp,
                        offsetY = 2.dp,
                        spread = 0.dp,
                    )
                    .dropShadow(
                        shape = CircleShape,
                        color = BbangZipTheme.color.primaryStrong_4B4137.copy(BbangZipTheme.opacity.opacity20),
                        blur = 5.dp,
                        offsetX = 0.dp,
                        offsetY = 2.dp,
                        spread = 0.dp,
                    ),
        )
    } else {
        TimerButton(
            icon = R.drawable.ic_start_default_80,
            contentDescription = "Start Button",
            style =
                TimerButtonStyle(
                    size = 80.dp,
                    backgroundColor = BbangZipTheme.color.primaryStrong_4B4137,
                    iconTint = BbangZipTheme.color.staticWhite_FFFFFF,
                ),
            onClick = onStartClick,
            modifier = modifier,
        )
    }
}

@Composable
fun SubTimerButton(
    icon: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    TimerButton(
        icon = icon,
        contentDescription = contentDescription,
        style =
            TimerButtonStyle(
                size = 48.dp,
                backgroundColor = Color.Transparent,
                borderColor = BbangZipTheme.color.secondaryStrong_F2EAE4,
                borderWidth = 1,
                iconTint = BbangZipTheme.color.primaryNormal_897869,
            ),
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    )
}

data class TimerButtonStyle(
    val size: Dp,
    val backgroundColor: Color,
    val borderColor: Color = Color.Transparent,
    val borderWidth: Int = 0,
    val iconTint: Color,
)

@Preview
@Composable
private fun TimerScreenPreview() {
    BBANGZIPANDROIDTheme {
        TimerScreen(
            timerState =
                TimerContract.TimerState(),
            sharedState =
                SharedContract.SharedState(),
        )
    }
}
