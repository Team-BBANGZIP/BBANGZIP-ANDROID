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
fun RestartBottomSheet(
    iisBottomSheetVisible: Boolean,
    onReturnBtnClick: () -> Unit,
    onRestartBtnClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TimerActionBottomSheet(
        isBottomSheetVisible = iisBottomSheetVisible,
        titleText = stringResource(R.string.restart_sheet_title),
        subTitleText = stringResource(R.string.restart_sheet_sub_title),
        leftBtnText = stringResource(R.string.re_sheet_left_btn),
        rightBtnText = stringResource(R.string.restart_sheet_right_btn),
        leftBtnIcon = R.drawable.ic_go_back_default_24,
        rightBtnIcon = R.drawable.ic_x_default_24,
        content = {
            Image(
                painter = painterResource(id = R.drawable.img_shine_bread_n1),
                contentDescription = null,
                modifier =
                    Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
            )
        },
        onLeftClick = { onReturnBtnClick() },
        onRightClick = { onRestartBtnClick() },
        onDismissRequest = { onDismissRequest() },
        modifier = modifier,
    )
}
