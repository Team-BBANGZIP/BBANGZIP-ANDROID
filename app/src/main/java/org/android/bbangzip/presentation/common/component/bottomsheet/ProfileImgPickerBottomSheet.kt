package org.android.bbangzip.presentation.common.component.bottomsheet

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.button.BbangZipButtonDefaults
import org.android.bbangzip.presentation.common.component.button.BbangzipBaseButton
import org.android.bbangzip.presentation.common.util.constant.OnboardingConstants
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.common.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileImgPickerBottomSheet(
    isBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit,
    onProfileImgItemClick: (Int) -> Unit,
    onCancelClick: () -> Unit,
    onCompleteClick: () -> Unit,
    selectedImgResId: Int,
) {
    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(R.string.profile_img_picker_title),
                style = BbangZipTheme.typography.title3SemiBold,
                color = BbangZipTheme.color.labelAlternative_A29D96,
            )

            Gap(height = 25.dp)
        },
        content = {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                contentAlignment = Alignment.Center,
            ) {
                ProfileImgChip(
                    imageResId = selectedImgResId,
                    size = 100.dp,
                    isSelected = false,
                    onClick = { },
                )
            }

            Gap(height = 24.dp)

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OnboardingConstants.PROFILE_IMG_RES_IDS
                    .chunked(3)
                    .forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Spacer(modifier = Modifier.weight(1f))

                            rowItems.forEachIndexed { index, imgResId ->
                                ProfileImgChip(
                                    imageResId = imgResId,
                                    size = 60.dp,
                                    isSelected = imgResId == selectedImgResId,
                                    onClick = { onProfileImgItemClick(imgResId) },
                                )

                                if (index < rowItems.lastIndex) {
                                    Gap(width = 32.dp)
                                }
                            }

                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
            }

            Gap(height = 32.dp)

            ProfileImgPickerBtn(
                onCancelClick = onCancelClick,
                onCompleteClick = onCompleteClick,
            )

            Gap(height = 27.dp)
        },
    )
}

@Composable
private fun ProfileImgPickerBtn(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onCompleteClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        BbangzipBaseButton(
            modifier = Modifier.weight(1f),
            onClick = onCancelClick,
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_x_default_24),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            },
            content = {
                Text(
                    text = stringResource(R.string.button_label_cancellation),
                    style = BbangZipTheme.typography.body2Medium,
                )
            },
        )

        Gap(width = 15.dp)

        BbangzipBaseButton(
            modifier = Modifier.weight(1f),
            onClick = onCompleteClick,
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_check_default_24),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            },
            colors =
                BbangZipButtonDefaults.colors(
                    enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                ),
            content = {
                Text(
                    text = stringResource(R.string.button_label_complete),
                    style = BbangZipTheme.typography.body2Medium,
                )
            },
        )
    }
}

@Composable
private fun ProfileImgChip(
    modifier: Modifier = Modifier,
    @DrawableRes imageResId: Int,
    size: Dp = 60.dp,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val outlineWidth = 3.dp
    val outlineColor = OnboardingConstants.PROFILE_IMG_OUTLINE_COLORS.getOrElse(imageResId) { OnboardingConstants.DEFAULT_OUTLINE_COLOR }

    val borderModifier =
        if (isSelected) {
            Modifier.border(
                width = outlineWidth,
                color = outlineColor,
                shape = CircleShape,
            )
        } else {
            Modifier
        }

    Box(
        modifier =
            modifier
                .size(size)
                .then(borderModifier)
                .padding(outlineWidth)
                .clip(CircleShape)
                .noRippleClickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProfileImgPickerBottomSheetPreview() {
    var isBottomSheetVisible by remember { mutableStateOf(true) }
    var selectedImg by remember { mutableIntStateOf(R.drawable.ic_profile_default_100) }

    BBANGZIPANDROIDTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.Blue,
                    ),
                onClick = { isBottomSheetVisible = true },
            ) {
                Text("바텀시트 띄우기")
            }
            Gap(100.dp)
            Text("selectedImg = $selectedImg")
        }
        ProfileImgPickerBottomSheet(
            isBottomSheetVisible = isBottomSheetVisible,
            onDismissRequest = { isBottomSheetVisible = false },
            onProfileImgItemClick = { newId -> selectedImg = newId },
            onCancelClick = { isBottomSheetVisible = false },
            // ViewModel에서는 OnCompleteClick에서 최종 저장 로직 넣을 것
            onCompleteClick = { isBottomSheetVisible = false },
            selectedImgResId = selectedImg,
        )
    }
}
