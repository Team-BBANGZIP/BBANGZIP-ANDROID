package org.android.bbangzip.presentation.ui.timer

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.component.toggle.BbangZipSegmentedButton
import org.android.bbangzip.presentation.model.TimerStatus
import org.android.bbangzip.presentation.model.getTitleText
import org.android.bbangzip.presentation.ui.shared.SharedContract
import org.android.bbangzip.presentation.ui.timer.component.CircularProgressBar
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.ui.theme.BbangZipTheme
import timber.log.Timber

@Composable
fun TimerScreen(
    timerState: TimerContract.TimerState,
    sharedState: SharedContract.SharedState,
    modifier: Modifier = Modifier,
    onBreadIconClick: () -> Unit = {},
    onResetBtnClick: () -> Unit = {},
    onRepeatSheetApproveBtnClick: () -> Unit = {},
    onRepeatSheetDismissBtnClick: () -> Unit = {},
    onRepeatBtnClick: () -> Unit = {},
    onStartBtnClick: () -> Unit = {},
    onStopBtnClick: () -> Unit = {},
    onTimeOptionToggleClick: (timeOption: Int) -> Unit = {},
    onBreadSelectionSheetClick: (breadId: Int) -> Unit = {},
    onCompleteSheetCheckBtnClick: () -> Unit = {},
    onCompleteSheetRetryBtnClick: () -> Unit = {},
    onCompleteSheetDismissRequest: () -> Unit = {},
    onResetSheetApproveBtnClick: () -> Unit = {},
    onResetSheetDismissBtnClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BbangZipTheme.brush.backgroundAccentGradient)
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Gap(17)

        Row(modifier = Modifier.padding(end = 20.dp)) {
            Gap()

            Row(
                modifier = Modifier
                    .border(width = 1.dp, color = BbangZipTheme.color.primaryLight_C8B5A2, shape = RoundedCornerShape(14.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_bread_default_24),
                    contentDescription = "Bread Icon",
                    tint = BbangZipTheme.color.primaryLight_C8B5A2
                )

                Gap(6)

                Text(
                    modifier = Modifier,
                    text = timerState.todayBreadCount.toString(),
                    style = BbangZipTheme.typography.label1SemiBold,
                    color = BbangZipTheme.color.primaryNormal_897869
                )
            }
        }

        Gap(50)

        Text(
            text = stringResource(timerState.timerStatus.getTitleText()),
            style = BbangZipTheme.typography.title2Medium,
            color = BbangZipTheme.color.labelAlternative_A29D96
        )

        Gap(24)

        CircularProgressBar(
            modifier = Modifier.padding(horizontal = 32.dp),
            progress = timerState.progress,
            progressMax = 100f,
            centerContent = { modifier ->
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = timerState.formattedTime,
                        style = BbangZipTheme.typography.timerExtraBold,
                        color = BbangZipTheme.color.primaryLight_C8B5A2
                    )
                }
            },
            bottomContent = { modifier ->
                val breadImg = if (timerState.timerStatus == TimerStatus.Idle) {
                    sharedState.breadImg
                } else {
                    timerState.breadImg
                }

                Image(
                    modifier = modifier.size(120.dp, 100.dp),
                    painter = painterResource(breadImg),
                    contentDescription = "Timer Icon",
                    alignment = Alignment.Center,
                )
            }
        )

        Gap(24)

        BbangZipSegmentedButton(
            options = listOf("30분", "60분"),
            indexOfSelectedOption = timerState.selectedTimeOptionIndex,
            onOptionSelect = { index -> onTimeOptionToggleClick(index) },
            modifier = Modifier.fillMaxWidth(0.25f),
        )

        Gap(57)

        Box(
            modifier = Modifier
                .background(
                    color = BbangZipTheme.color.primaryStrong_4B4137,
                    shape = CircleShape
                )
                .size(80.dp)
                .clickable { onStartBtnClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_start_default_80),
                contentDescription = "start button",
                tint = BbangZipTheme.color.staticWhite_FFFFFF
            )

        }
    }
}

@Preview
@Composable
private fun TimerScreenPreview() {

}