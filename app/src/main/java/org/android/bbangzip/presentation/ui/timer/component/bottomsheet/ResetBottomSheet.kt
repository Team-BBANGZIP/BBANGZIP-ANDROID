package org.android.bbangzip.presentation.ui.timer.component.bottomsheet

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import kotlinx.serialization.json.JsonNull.content
import org.android.bbangzip.R
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun ResetBottomSheet(
    iisBottomSheetVisible: Boolean,
    remainingTime: Long,
    onReturnBtnClick: () -> Unit,
    onResetBtnClick: () -> Unit,
    onDismissRequest: () -> Unit,
    timeOptionIndex: Int,
    modifier: Modifier = Modifier
) {
    val totalSeconds = remainingTime / 1000L

    val remainingTimeText = if (totalSeconds >= 60) {
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
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.img_shine_bread_n2),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                )
            }
        },
        onLeftClick = { onReturnBtnClick() },
        onRightClick = { onResetBtnClick() },
        onDismissRequest = { onDismissRequest() },
        modifier = modifier
    )
}
