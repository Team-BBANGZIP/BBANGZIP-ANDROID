package org.android.bbangzip.presentation.ui.profileedit

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.bottomsheet.CommitmentBottomSheet
import org.android.bbangzip.presentation.common.component.bottomsheet.ProfileImgPickerBottomSheet
import org.android.bbangzip.presentation.common.component.textfield.BbangZipUnderLinedTextField
import org.android.bbangzip.presentation.common.component.topbar.BbangZipBaseTopBar
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.common.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun ProfileEditScreen(
    state: ProfileEditContract.ProfileEditState,
    onBackIconClick: () -> Unit = {},
    onProfileImageClick: () -> Unit = {},
    onCommitmentAreaClick: () -> Unit = {},
    onNicknameChange: (String) -> Unit = {},
    onProfileImageBottomSheetDismissRequest: () -> Unit = {},
    onSelectProfileImage: (Int) -> Unit = {},
    onProfileImageCancelBtnClick: () -> Unit = {},
    onProfileImageCompleteBtnClick: () -> Unit = {},
    onCommitmentBottomSheetDismissRequest: () -> Unit = {},
    onCommitmentMessageChange: (String) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(BbangZipTheme.color.backgroundNormal_FFFFFF)
                .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BbangZipBaseTopBar(
            title = stringResource(R.string.my_profile_edit_title),
            titleColor = BbangZipTheme.color.labelNormal_6B6560,
            titleStyle = BbangZipTheme.typography.title2Medium,
            backGroundColor = BbangZipTheme.color.backgroundNormal_FFFFFF,
            leadingIcon = R.drawable.ic_arrow_left_24,
            leadingIconColor = BbangZipTheme.color.labelAssistive_C9C7C5,
            onLeadingIconClick = onBackIconClick,
        )

        Gap(height = 28.dp)

        ProfileImageArea(
            currentProfileResId = state.profileImageResId,
            onClick = onProfileImageClick,
        )

        Gap(height = 48.dp)

        NicknameArea(
            nickname = state.nickname,
            onNicknameChange = onNicknameChange,
            focusManager = focusManager,
            focusRequester = focusRequester,
        )

        Gap(height = 48.dp)

        CommitmentMessageArea(
            commitmentMessage = state.commitmentMessage,
            onCommitmentAreaClick = onCommitmentAreaClick,
        )
    }

    ProfileImgPickerBottomSheet(
        confirmButtonLabel = stringResource(R.string.button_label_save),
        isBottomSheetVisible = state.isProfileImgBottomSheetVisible,
        onDismissRequest = onProfileImageBottomSheetDismissRequest,
        onProfileImgItemClick = onSelectProfileImage,
        onCancelClick = onProfileImageCancelBtnClick,
        onCompleteClick = onProfileImageCompleteBtnClick,
        selectedImgResId = state.selectedProfileImageResId,
    )

    CommitmentBottomSheet(
        isBottomSheetVisible = state.isCommitmentBottomSheetVisible,
        onDismissRequest = onCommitmentBottomSheetDismissRequest,
        focusManager = focusManager,
        commitmentMessage = state.commitmentMessage,
        oncommitmentMessageChange = onCommitmentMessageChange,
        onDoneAction = onCommitmentBottomSheetDismissRequest,
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
private fun NicknameArea(
    nickname: String,
    focusManager: FocusManager,
    focusRequester: FocusRequester,
    onNicknameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
        ) {
            Text(
                text = stringResource(R.string.my_profile_edit_name),
            )

            Gap(height = 20.dp)

            BbangZipUnderLinedTextField(
                value = nickname,
                onValueChange = onNicknameChange,
                placeholder = R.string.onboarding_name_description,
                focusManager = focusManager,
                focusRequester = focusRequester,
            )
        }
    }
}

@Composable
private fun CommitmentMessageArea(
    commitmentMessage: String,
    modifier: Modifier = Modifier,
    onCommitmentAreaClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.my_profile_commitment_message),
                style = BbangZipTheme.typography.body1Medium,
                color = BbangZipTheme.color.labelNormal_6B6560,
            )

            Gap()

            Icon(
                modifier =
                    Modifier
                        .noRippleClickable(onClick = onCommitmentAreaClick)
                        .padding(4.dp),
                painter = painterResource(R.drawable.ic_arrow_right_24),
                contentDescription = null,
                tint = BbangZipTheme.color.labelAlternative_A29D96,
            )
        }

        Gap(height = 20.dp)

        Box(
            modifier =
                Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .background(
                        color = BbangZipTheme.color.componentStrong_F6F6F5,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(horizontal = 12.dp, vertical = 11.dp),
        ) {
            Text(
                text = commitmentMessage,
                style = BbangZipTheme.typography.body1Medium,
                color = BbangZipTheme.color.labelNormal_6B6560,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileEditScreenPreview() {
    BBANGZIPANDROIDTheme {
        ProfileEditScreen(
            state =
                ProfileEditContract.ProfileEditState(
                    profileImageResId = R.drawable.ic_profile_default_100,
                    nickname = "김재민",
                    commitmentMessage = "빵을 굽자",
                ),
        )
    }
}
