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

@Composable
fun CompleteBottomSheet(
    iisBottomSheetVisible: Boolean,
    onRestartBtnClick: () -> Unit,
    onCheckTodoBtnClick: () -> Unit,
    onDismissRequest: () -> Unit,
    timeOptionIndex: Int,
    modifier: Modifier = Modifier,
) {
    val leftBtnText =
        if (timeOptionIndex == 0) {
            stringResource(R.string.complete_sheet_left_btn_thirty)
        } else {
            stringResource(R.string.complete_sheet_left_btn_sixty)
        }

    val subTitleText =
        if (timeOptionIndex == 0) {
            stringResource(R.string.complete_sheet_sub_title_n1)
        } else {
            stringResource(R.string.complete_sheet_left_btn_sixty)
        }
    TimerActionBottomSheet(
        isBottomSheetVisible = iisBottomSheetVisible,
        titleText = stringResource(R.string.complete_sheet_title),
        subTitleText = subTitleText,
        leftBtnText = leftBtnText,
        rightBtnText = stringResource(R.string.complete_sheet_right_btn),
        leftBtnIcon = R.drawable.ic_plus_default_24,
        rightBtnIcon = R.drawable.ic_book_default_24,
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
        onLeftClick = { onRestartBtnClick() },
        onRightClick = { onCheckTodoBtnClick() },
        onDismissRequest = { onDismissRequest() },
        modifier = modifier,
        isEqualWeight = false,
    )
}
