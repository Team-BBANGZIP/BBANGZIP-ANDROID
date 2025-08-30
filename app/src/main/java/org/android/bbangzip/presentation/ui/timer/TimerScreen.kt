package org.android.bbangzip.presentation.ui.timer

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.component.button.BbangZipButtonDefaults
import org.android.bbangzip.presentation.component.button.BbangzipBaseButton
import org.android.bbangzip.presentation.component.toggle.BbangZipSegmentedButton
import org.android.bbangzip.presentation.model.TimerStatus
import org.android.bbangzip.presentation.model.getTimerFontColor
import org.android.bbangzip.presentation.model.getTitleText
import org.android.bbangzip.presentation.ui.shared.SharedContract
import org.android.bbangzip.presentation.ui.timer.component.CircularProgressBar
import org.android.bbangzip.presentation.ui.timer.component.bottomsheet.BreadSelectBottomSheet
import org.android.bbangzip.presentation.ui.timer.component.bottomsheet.CompleteBottomSheet
import org.android.bbangzip.presentation.ui.timer.component.bottomsheet.ResetBottomSheet
import org.android.bbangzip.presentation.ui.timer.component.bottomsheet.RestartBottomSheet
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun TimerScreen(
    timerState: TimerContract.TimerState,
    sharedState: SharedContract.SharedState,
    modifier: Modifier = Modifier,
    onBreadIconClick: () -> Unit = {},
    onResetBtnClick: () -> Unit = {},
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
    onResetSheetApproveBtnClick: () -> Unit = {},
    onResetSheetDismissBtnClick: () -> Unit = {},
) {
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

        Text(
            text = stringResource(timerState.timerStatus.getTitleText()),
            style = BbangZipTheme.typography.title2Medium,
            color = BbangZipTheme.color.labelAlternative_A29D96,
        )

        Gap(24.dp)

        CircularProgressBar(
            modifier = Modifier.padding(horizontal = 32.dp),
            progress = timerState.progress,
            progressMax = 100f,
            centerContent = { modifier ->
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = timerState.formattedTime,
                        style = BbangZipTheme.typography.timerExtraBold,
                        color = timerState.timerStatus.getTimerFontColor(),
                    )
                }
            },
            bottomContent = { mod ->
                val breadImg =
                    if (timerState.timerStatus == TimerStatus.Idle) {
                        sharedState.breadImg
                    } else {
                        timerState.breadImg
                    }

                BreadWithTriangleIndicator(
                    modifier = mod,
                    breadImageRes = breadImg,
                    showTriangle = timerState.timerStatus == TimerStatus.Idle,
                    isClickable = timerState.timerStatus == TimerStatus.Idle,
                    onClick = onBreadIconClick
                )
            },
        )

        Gap(24.dp)

        BbangZipSegmentedButton(
            options = listOf("30분", "60분"),
            indexOfSelectedOption = timerState.selectedTimeOptionIndex,
            onOptionSelect = { index -> onTimeOptionToggleClick(index) },
            modifier =
            Modifier
                .fillMaxWidth(0.25f)
                .alpha(
                    if (timerState.timerStatus != TimerStatus.Idle) 0f else 1f,
                ),
            enabled = timerState.timerStatus == TimerStatus.Idle,
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
            if (timerState.timerStatus != TimerStatus.Idle) {
                SubTimerButton(
                    icon = R.drawable.ic_return_default_24,
                    contentDescription = "Restart Button",
                    onClick = onRestartBtnClick,
                    enabled = timerState.timerStatus != TimerStatus.Idle,
                )
            }

            Gap(16.dp)
            // Start/Stop 버튼
            MainTimerButton(
                isRunning = timerState.timerStatus == TimerStatus.Running,
                onStartClick = onStartBtnClick,
                onStopClick = onStopBtnClick,
            )

            Gap(16.dp)
            // Reset 버튼
            if (timerState.timerStatus != TimerStatus.Idle) {
                SubTimerButton(
                    icon = R.drawable.ic_stop_default_24,
                    contentDescription = "Reset Button",
                    onClick = onResetBtnClick,
                    enabled = timerState.timerStatus != TimerStatus.Idle,
                )
            }
        }

        BreadSelectBottomSheet(
            currentBreadId = sharedState.breadId,
            breadList = timerState.breadList,
            isBottomSheetVisible = timerState.isBreadSelectionSheetVisible,
            breadCount = timerState.todayBreadCount,
            onDismissRequest = { onBreadSelectionSheetDismissRequest() },
            onBreadSelect = { breadId -> onBreadSelectionSheetClick(breadId) },
        )

        ResetBottomSheet(
            iisBottomSheetVisible = timerState.isResetSheetVisible,
            remainingTime = timerState.remainingTime,
            onReturnBtnClick = { onResetSheetDismissBtnClick() },
            onResetBtnClick = { onResetSheetApproveBtnClick() },
            onDismissRequest = { onResetSheetDismissBtnClick() },
            timeOptionIndex = timerState.selectedTimeOptionIndex,
        )

        RestartBottomSheet(
            iisBottomSheetVisible = timerState.isRestartSheetVisible,
            onReturnBtnClick = { onRestartSheetDismissBtnClick() },
            onRestartBtnClick = { onRestartSheetApproveBtnClick() },
            onDismissRequest = { onRestartSheetDismissBtnClick() },
        )

        CompleteBottomSheet(
            iisBottomSheetVisible = timerState.isCompleteSheetVisible,
            onRestartBtnClick = { onCompleteSheetRestartBtnClick() },
            onCheckTodoBtnClick = { onCompleteSheetCheckBtnClick() },
            onDismissRequest = { onCompleteSheetDismissRequest() },
            timeOptionIndex = timerState.selectedTimeOptionIndex,
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
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 20.dp)
    ) {
        Gap()

        BreadCounter(
            count = breadCount,
            onClick = onBreadCountClick
        )
    }
}

@Composable
fun BreadCounter(
    count: Int,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit) = { }
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = BbangZipTheme.color.primaryLight_C8B5A2,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable { onClick() },
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

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun AnimatedTriangleIndicator(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    animationDuration: Int = 800,
    offsetRange: Float = 3f,
    yOffset: Float = -90f,
    tint: Color = BbangZipTheme.color.primaryNormal_897869
) {
    val infiniteTransition = rememberInfiniteTransition(label = "triangle animation")
    val triangleOffset by infiniteTransition.animateFloat(
        initialValue = -offsetRange,
        targetValue = offsetRange,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "triangle offset",
    )

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300)),
        modifier = modifier.offset(y = (yOffset + triangleOffset).dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_triangle_down_24),
            contentDescription = "moving triangle",
            tint = tint,
        )
    }
}

@Composable
fun InteractiveBreadImage(
    breadImageRes: Int,
    isClickable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    width: Dp = 120.dp,
    height: Dp = 100.dp
) {
    Image(
        modifier = modifier
            .size(width, height)
            .noRippleClickable(enabled = isClickable) { onClick() },
        painter = painterResource(breadImageRes),
        contentDescription = "Timer Icon",
    )
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun BreadWithTriangleIndicator(
    breadImageRes: Int,
    showTriangle: Boolean,
    isClickable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    breadSize: DpSize = DpSize(120.dp, 100.dp),

    ) {
    val infiniteTransition = rememberInfiniteTransition(label = "triangle animation")
    val triangleOffset by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "triangle offset",
    )

    AnimatedVisibility(
        visible = showTriangle,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300)),
        modifier = modifier.offset(y = (-90 + triangleOffset).dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_triangle_down_24),
            contentDescription = "moving triangle",
            tint = BbangZipTheme.color.primaryNormal_897869,
        )
    }
    Image(
        modifier = modifier
            .size(breadSize.width, breadSize.height)
            .noRippleClickable(enabled = isClickable) { onClick() },
        painter = painterResource(breadImageRes),
        contentDescription = "Timer Icon",
    )
}


@Composable
fun TimerButton(
    icon: Int,
    style: TimerButtonStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
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
                } else this
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
    modifier: Modifier = Modifier
) {
    if (isRunning) {
        TimerButton(
            icon = R.drawable.ic_pause_default_80,
            contentDescription = "Stop Button",
            style = TimerButtonStyle(
                size = 80.dp,
                backgroundColor = BbangZipTheme.color.secondaryStrong_F2EAE4,
                iconTint = BbangZipTheme.color.primaryNormal_897869
            ),
            onClick = onStopClick,
            modifier = modifier
        )
    } else {
        TimerButton(
            icon = R.drawable.ic_start_default_80,
            contentDescription = "Start Button",
            style = TimerButtonStyle(
                size = 80.dp,
                backgroundColor = BbangZipTheme.color.primaryStrong_4B4137,
                iconTint = BbangZipTheme.color.staticWhite_FFFFFF
            ),
            onClick = onStartClick,
            modifier = modifier
        )
    }
}

@Composable
fun SubTimerButton(
    icon: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    TimerButton(
        icon = icon,
        contentDescription = contentDescription,
        style = TimerButtonStyle(
            size = 48.dp,
            backgroundColor = Color.Transparent,
            borderColor = BbangZipTheme.color.secondaryStrong_F2EAE4,
            borderWidth = 1,
            iconTint = BbangZipTheme.color.primaryNormal_897869
        ),
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    )
}

data class TimerButtonStyle(
    val size: Dp,
    val backgroundColor: Color,
    val borderColor: Color = Color.Transparent,
    val borderWidth: Int = 0,
    val iconTint: Color
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
