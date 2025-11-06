package org.android.bbangzip.presentation.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.bottomsheet.ProfileImgPickerBottomSheet
import org.android.bbangzip.presentation.common.component.bottomsheet.ProfileNicknameInputBottomSheet
import org.android.bbangzip.presentation.common.component.button.BbangZipButtonDefaults
import org.android.bbangzip.presentation.common.component.button.BbangzipBaseButton
import org.android.bbangzip.presentation.common.component.topbar.BbangZipBaseTopBar
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.common.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme
import org.android.bbangzip.ui.theme.defaultBbangZipColor
import org.android.bbangzip.ui.theme.defaultBbangZipTypography

@Composable
fun OnboardingScreen(
    state: OnboardingContract.OnboardingState,
    onNicknameChange: (String) -> Unit = {},
    onClickNicknameTextField: () -> Unit = {},
    onClickNicknameInputBottomSheetDismissRequest: () -> Unit = {},
    onNicknameInputDoneAction: () -> Unit = {},
    onClickProfileImg: () -> Unit = {},
    onSelectProfileImg: (Int) -> Unit = {},
    onClickProfileImgBottomSheetDismissRequest: () -> Unit = {},
    onClickProfileImgCancelBtn: () -> Unit = {},
    onClickProfileImgCompleteBtn: () -> Unit = {},
    onClickBackBtn: () -> Unit = {},
    onClickSaveBtn: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(defaultBbangZipColor.backgroundNormal_FFFFFF)
                .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BbangZipBaseTopBar(
            title = stringResource(R.string.onboarding_title),
            titleStyle = BbangZipTheme.typography.body1Medium,
            titleColor = BbangZipTheme.color.labelStrong_463D34,
            leadingIcon = R.drawable.ic_arrow_left_24,
            onLeadingIconClick = onClickBackBtn,
        )

        Gap(height = 32.dp)

        ProfileImageArea(
            currentProfileResId = state.profileImg,
            onClick = onClickProfileImg,
        )

        Gap(height = 48.dp)

        NicknameClickableField(
            value = state.nickname,
            onClick = onClickNicknameTextField,
            placeholder = R.string.onboarding_name_description,
        )

        Spacer(modifier = Modifier.weight(1f))

        BbangzipBaseButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            enabled = state.isSaveBtnEnabled,
            onClick = onClickSaveBtn,
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_x_default_24),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            },
            colors =
                BbangZipButtonDefaults.colors(
                    enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                    enabledContentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    disabledContainerColor = BbangZipTheme.color.labelDisable_E4E2E0,
                    disabledContentColor = BbangZipTheme.color.labelAssistive_C9C7C5,
                ),
            content = {
                Text(
                    text = stringResource(R.string.button_label_save),
                    style = BbangZipTheme.typography.body2Medium,
                )
            },
        )
    }

    ProfileNicknameInputBottomSheet(
        isBottomSheetVisible = state.isNicknameBottomSheetVisible,
        onDismissRequest = onClickNicknameInputBottomSheetDismissRequest,
        nickname = state.nickname,
        focusManager = focusManager,
        onNicknameChange = onNicknameChange,
        onDoneAction = {
            focusManager.clearFocus()
            onNicknameInputDoneAction()
        },
    )

    ProfileImgPickerBottomSheet(
        isBottomSheetVisible = state.isProfileImgBottomSheetVisible,
        onDismissRequest = onClickProfileImgBottomSheetDismissRequest,
        onProfileImgItemClick = onSelectProfileImg,
        onCancelClick = onClickProfileImgCancelBtn,
        onCompleteClick = onClickProfileImgCompleteBtn,
        selectedImgResId = state.selectedImg,
    )
}

@Composable
private fun ProfileImageArea(
    @DrawableRes currentProfileResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(100.dp)
                .noRippleClickable(onClick = onClick),
    ) {
        Image(
            painter = painterResource(id = currentProfileResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center),
        )

        Image(
            painter = painterResource(R.drawable.ic_profile_with_circle_24),
            contentDescription = null,
            modifier =
                Modifier
                    .size(24.dp)
                    .align(Alignment.BottomEnd),
        )
    }
}

@Composable
private fun NicknameClickableField(
    value: String,
    onClick: () -> Unit,
    @StringRes placeholder: Int,
    modifier: Modifier = Modifier,
) {
    val textColor =
        if (value.isEmpty()) {
            defaultBbangZipColor.labelAssistive_C9C7C5
        } else {
            defaultBbangZipColor.labelNormal_6B6560
        }

    val textToShow =
        if (value.isEmpty()) {
            stringResource(placeholder)
        } else {
            value
        }

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .noRippleClickable(onClick = onClick),
    ) {
        Text(
            text = textToShow,
            style = defaultBbangZipTypography.body1Medium,
            color = textColor,
            modifier = Modifier.padding(start = 2.dp),
        )

        Gap(height = 8.dp)

        HorizontalDivider(
            thickness = 2.dp,
            color = defaultBbangZipColor.primaryNormal_897869,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    BBANGZIPANDROIDTheme {
        OnboardingScreen(
            state = OnboardingContract.OnboardingState(),
        )
    }
}
