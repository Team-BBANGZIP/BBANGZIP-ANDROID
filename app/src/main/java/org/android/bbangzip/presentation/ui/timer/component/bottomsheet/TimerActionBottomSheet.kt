package org.android.bbangzip.presentation.ui.timer.component.bottomsheet

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import org.android.bbangzip.presentation.component.bottomsheet.BbangZipBottomSheetSlot
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BbangZipTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerActionBottomSheet(
    isBottomSheetVisible: Boolean,
    @StringRes titleText: Int,
    @StringRes subTitleText: Int,
    content: @Composable () -> Unit,
    @StringRes leftBtnText: Int,
    @StringRes rightBtnText: Int,
    @DrawableRes leftBtnIcon: Int,
    @DrawableRes rightBtnIcon: Int,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    onDismissRequest: () -> Unit,
    timeOptionIndex: Int,
    modifier: Modifier = Modifier,
) {
    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        contentPadding = contentPadding(top = 40.dp),
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(titleText),
                    color = BbangZipTheme.color.primaryNormal_897869,
                    style = BbangZipTheme.typography.title1SemiBold,
                )

                Gap(4)


                Text(
                    text = stringResource(subTitleText),
                    color = BbangZipTheme.color.labelAlternative_A29D96,
                    style = BbangZipTheme.typography.body1Medium,
                )
            }

            Gap(28)
        },
        content = {
            content()
        },
        interactRow = {
            Gap(42)

            Row(
                modifier =
                Modifier
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(color = BbangZipTheme.color.primaryNormal_897869, shape = RoundedCornerShape(size = 32.dp))
                        .noRippleClickable { onLeftClick() },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(leftBtnText),
                        color = BbangZipTheme.color.labelAlternative_A29D96,
                        style = BbangZipTheme.typography.subTitle1Medium,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(id = leftBtnIcon),
                        contentDescription = null,
                        tint = BbangZipTheme.color.primaryNormal_897869,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .background(color = BbangZipTheme.color.primaryStrong_4B4137, shape = RoundedCornerShape(size = 32.dp))
                        .noRippleClickable { onRightClick() },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(rightBtnText),
                        color = BbangZipTheme.color.labelAlternative_A29D96,
                        style = BbangZipTheme.typography.subTitle1Medium,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(id = rightBtnIcon),
                        contentDescription = null,
                        tint = BbangZipTheme.color.primaryNormal_897869,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
    )
}
