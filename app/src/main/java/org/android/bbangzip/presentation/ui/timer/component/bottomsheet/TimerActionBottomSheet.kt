package org.android.bbangzip.presentation.ui.timer.component.bottomsheet

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
    titleText: String,
    subTitleText: String,
    leftBtnText: String,
    rightBtnText: String,
    @DrawableRes leftBtnIcon: Int,
    @DrawableRes rightBtnIcon: Int,
    content: @Composable () -> Unit,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    isEqualWeight: Boolean = true,
) {
    val (leftWeight, rightWeight) = if (isEqualWeight) 1f to 1f else 1f to 1.5f

    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        contentPadding = contentPadding(top = 40.dp, start = 0.dp, end = 0.dp, bottom = 0.dp),
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = titleText,
                    color = BbangZipTheme.color.primaryNormal_897869,
                    style = BbangZipTheme.typography.title1SemiBold,
                )

                Gap(4)

                Text(
                    text = subTitleText,
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
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = BbangZipTheme.color.primaryNormal_897869, shape = RoundedCornerShape(size = 32.dp))
                        .noRippleClickable { onLeftClick() }
                        .padding(vertical = 14.dp)
                        .weight(leftWeight),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = leftBtnText,
                        color = BbangZipTheme.color.staticWhite_FFFFFF,
                        style = BbangZipTheme.typography.body2Medium,
                        modifier = Modifier,
                    )

                    Gap(4)

                    Icon(
                        imageVector = ImageVector.vectorResource(id = leftBtnIcon),
                        contentDescription = null,
                        tint = BbangZipTheme.color.staticWhite_FFFFFF,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = BbangZipTheme.color.primaryStrong_4B4137, shape = RoundedCornerShape(size = 32.dp))
                        .noRippleClickable { onRightClick() }
                        .padding(vertical = 16.dp)
                        .weight(rightWeight),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = rightBtnText,
                        color = BbangZipTheme.color.staticWhite_FFFFFF,
                        style = BbangZipTheme.typography.body2Medium,
                        modifier = Modifier,
                    )

                    Gap(4)

                    Icon(
                        imageVector = ImageVector.vectorResource(id = rightBtnIcon),
                        contentDescription = null,
                        tint = BbangZipTheme.color.staticWhite_FFFFFF,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Gap(12)
        }
    )
}
