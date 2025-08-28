package org.android.bbangzip.presentation.ui.timer.component.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R

private data class ResetBottomSheetState(
    val breadCount: Int,
    val breadImg: Int,
    val minuteStringRes: Int
)

@Composable
fun ResetBottomSheet(
    iisBottomSheetVisible: Boolean,
    remainingTime: Long,
    onReturnBtnClick: () -> Unit,
    onResetBtnClick: () -> Unit,
    onDismissRequest: () -> Unit,
    timeOptionIndex: Int,
    modifier: Modifier = Modifier,
) {
    val totalSeconds = remainingTime / 1000L
    val minutes = totalSeconds / 60L

    val sheetState = getBottomSheetState(timeOptionIndex, minutes)

    val remainingTimeText = if (totalSeconds >= 60) {
        stringResource(sheetState.minuteStringRes, minutes)
    } else {
        stringResource(R.string.reset_sheet_sub_title_second, totalSeconds)
    }


    TimerActionBottomSheet(
        isBottomSheetVisible = iisBottomSheetVisible,
        titleText = stringResource(R.string.reset_sheet_title),
        subTitleText = remainingTimeText,
        leftBtnText = stringResource(R.string.re_sheet_left_btn),
        rightBtnText = stringResource(R.string.reset_sheet_right_btn),
        leftBtnIcon = R.drawable.ic_go_back_default_24,
        rightBtnIcon = R.drawable.ic_x_default_24,
        content = {
            Image(
                painter = painterResource(id = sheetState.breadImg),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
            )
        },
        onLeftClick = { onReturnBtnClick() },
        onRightClick = { onResetBtnClick() },
        onDismissRequest = { onDismissRequest() },
        modifier = modifier,
    )
}

private fun getBottomSheetState(timeOptionIndex: Int, minutes: Long): ResetBottomSheetState {
    val breadCount = if (timeOptionIndex == 0 || minutes <= 30) 1 else 2

    return ResetBottomSheetState(
        breadCount = breadCount,
        breadImg = if (breadCount == 1) R.drawable.img_shine_bread_n1 else R.drawable.img_shine_bread_n2,
        minuteStringRes = if (breadCount == 1) R.string.reset_sheet_sub_title_minute_n1 else R.string.reset_sheet_sub_title_minute_n2
    )
}