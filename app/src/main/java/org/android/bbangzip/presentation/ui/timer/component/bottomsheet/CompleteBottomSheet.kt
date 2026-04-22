package org.android.bbangzip.presentation.ui.timer.component.bottomsheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import org.android.bbangzip.R

@Composable
fun CompleteBottomSheet(
    isBottomSheetVisible: Boolean,
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
            stringResource(R.string.complete_sheet_sub_title_n2)
        }
    val rawRes = if (timeOptionIndex == 0) R.raw.one_bread_success_lottie else R.raw.two_bread_success_lottie
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(rawRes))
    TimerActionBottomSheet(
        isBottomSheetVisible = isBottomSheetVisible,
        titleText = stringResource(R.string.complete_sheet_title),
        subTitleText = subTitleText,
        leftBtnText = leftBtnText,
        rightBtnText = stringResource(R.string.complete_sheet_right_btn),
        leftBtnIcon = R.drawable.ic_plus_default_24,
        rightBtnIcon = R.drawable.ic_book_default_24,
        content = {
            LottieAnimation(
                composition = composition,
                iterations = 1,
                modifier =
                    Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
            )
        },
        onLeftClick = onRestartBtnClick,
        onRightClick = onCheckTodoBtnClick,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        isEqualWeight = false,
    )
}
