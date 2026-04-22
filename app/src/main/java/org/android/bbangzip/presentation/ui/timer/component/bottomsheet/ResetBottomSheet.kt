package org.android.bbangzip.presentation.ui.timer.component.bottomsheet

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.ui.timer.util.TimerConstants
import java.util.concurrent.TimeUnit

private data class ResetBottomSheetInfo(
    val title: String,
    @DrawableRes val breadImg: Int,
)

@Composable
fun ResetBottomSheet(
    isBottomSheetVisible: Boolean,
    remainingTime: Long,
    timeOptionIndex: Int,
    onReturnBtnClick: () -> Unit,
    onResetBtnClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetInfo = getBottomSheetState(timeOptionIndex, remainingTime)

    TimerActionBottomSheet(
        isBottomSheetVisible = isBottomSheetVisible,
        titleText = stringResource(R.string.reset_sheet_title),
        subTitleText = sheetInfo.title,
        leftBtnText = stringResource(R.string.re_sheet_left_btn),
        rightBtnText = stringResource(R.string.reset_sheet_right_btn),
        leftBtnIcon = R.drawable.ic_go_back_default_24,
        rightBtnIcon = R.drawable.ic_x_default_24,
        content = {
            Image(
                painter = painterResource(id = sheetInfo.breadImg),
                contentDescription = null,
                modifier =
                    Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
            )
        },
        onLeftClick = onReturnBtnClick,
        onRightClick = onResetBtnClick,
        onDismissRequest = onDismiss,
        modifier = modifier,
    )
}

private fun getTotalDurationMillis(timeOptionIndex: Int): Long {
    return when (timeOptionIndex) {
        0 -> TimerConstants.THIRTY_MINUTES
        1 -> TimerConstants.SIXTY_MINUTES
        else -> 0L
    }
}

@Composable
private fun getBottomSheetState(
    timeOptionIndex: Int,
    remainingTime: Long,
): ResetBottomSheetInfo {
    val totalDurationMillis = getTotalDurationMillis(timeOptionIndex)
    val elapsedTimeMillis = totalDurationMillis - remainingTime

    val milestone30min = TimerConstants.THIRTY_MINUTES
    val milestone60min = TimerConstants.SIXTY_MINUTES

    return when (timeOptionIndex) {
        0 -> {
            val timeToMilestoneMillis = remainingTime
            val secondsToMilestone = TimeUnit.MILLISECONDS.toSeconds(timeToMilestoneMillis).coerceAtLeast(0)

            val title =
                if (secondsToMilestone < 60) {
                    stringResource(R.string.reset_sheet_sub_title_second_n1, secondsToMilestone)
                } else {
                    val minutesToMilestone = TimeUnit.MILLISECONDS.toMinutes(timeToMilestoneMillis).coerceAtLeast(1)
                    stringResource(R.string.reset_sheet_sub_title_minute_n1, minutesToMilestone)
                }
            ResetBottomSheetInfo(title = title, breadImg = R.drawable.img_one_bread_stop_timer)
        }

        1 -> {
            if (elapsedTimeMillis < milestone30min) {
                val timeToMilestoneMillis = milestone30min - elapsedTimeMillis
                val secondsToMilestone = TimeUnit.MILLISECONDS.toSeconds(timeToMilestoneMillis).coerceAtLeast(0)
                val title =
                    if (secondsToMilestone < 60) {
                        stringResource(R.string.reset_sheet_sub_title_second_n1, secondsToMilestone)
                    } else {
                        val minutesToMilestone = TimeUnit.MILLISECONDS.toMinutes(timeToMilestoneMillis).coerceAtLeast(1)
                        stringResource(R.string.reset_sheet_sub_title_minute_n1, minutesToMilestone)
                    }
                ResetBottomSheetInfo(title = title, breadImg = R.drawable.img_one_bread_stop_timer)
            } else {
                val timeToMilestoneMillis = milestone60min - elapsedTimeMillis
                val secondsToMilestone = TimeUnit.MILLISECONDS.toSeconds(timeToMilestoneMillis).coerceAtLeast(0)
                val title =
                    if (secondsToMilestone < 60) {
                        stringResource(R.string.reset_sheet_sub_title_second_n2, secondsToMilestone)
                    } else {
                        val minutesToMilestone = TimeUnit.MILLISECONDS.toMinutes(timeToMilestoneMillis).coerceAtLeast(1)
                        stringResource(R.string.reset_sheet_sub_title_minute_n2, minutesToMilestone)
                    }
                ResetBottomSheetInfo(title = title, breadImg = R.drawable.img_two_bread_stop_timer)
            }
        }

        else ->
            ResetBottomSheetInfo(
                title = stringResource(id = R.string.reset_sheet_title),
                breadImg = R.drawable.img_one_bread_stop_timer,
            )
    }
}
