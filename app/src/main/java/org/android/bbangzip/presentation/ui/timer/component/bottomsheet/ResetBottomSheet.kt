package org.android.bbangzip.presentation.ui.timer.component.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.JsonNull.content
import org.android.bbangzip.R

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

    val remainingTimeText =
        if (totalSeconds >= 60) {
            val minutes = totalSeconds / 60L
            stringResource(R.string.reset_sheet_sub_title_minute, minutes)
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
            if (timeOptionIndex == 0) {
                Image(
                    painter = painterResource(id = R.drawable.img_shine_bread_n1),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.img_shine_bread_n2),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                )
            }
        },
        onLeftClick = { onReturnBtnClick() },
        onRightClick = { onResetBtnClick() },
        onDismissRequest = { onDismissRequest() },
        modifier = modifier,
    )
}
