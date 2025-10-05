package org.android.bbangzip.presentation.ui.timer.component.bottomsheet

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import org.android.bbangzip.presentation.common.component.bottomsheet.BbangZipBottomSheetSlot
import org.android.bbangzip.presentation.common.component.button.BbangZipButtonDefaults
import org.android.bbangzip.presentation.common.component.button.BbangzipBaseButton
import org.android.bbangzip.presentation.common.util.extension.Gap
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

                Gap(4.dp)

                Text(
                    text = subTitleText,
                    color = BbangZipTheme.color.labelAlternative_A29D96,
                    style = BbangZipTheme.typography.body1Medium,
                )
            }

            Gap(28.dp)
        },
        content = {
            content()
        },
        interactRow = {
            Gap(42.dp)

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                BbangzipBaseButton(
                    onClick =  onLeftClick,
                    modifier = Modifier.weight(leftWeight),
                    colors =
                        BbangZipButtonDefaults.colors(
                            enabledContainerColor = BbangZipTheme.color.primaryNormal_897869,
                            enabledContentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                        ),
                    trailingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = leftBtnIcon),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    content = {
                        Text(
                            text = leftBtnText,
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                BbangzipBaseButton(
                    onClick = onRightClick,
                    modifier = Modifier.weight(rightWeight),
                    colors =
                        BbangZipButtonDefaults.colors(
                            enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                            enabledContentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                        ),
                    trailingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = rightBtnIcon),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    content = {
                        Text(
                            text = rightBtnText,
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )
            }

            Gap(12.dp)
        },
    )
}
