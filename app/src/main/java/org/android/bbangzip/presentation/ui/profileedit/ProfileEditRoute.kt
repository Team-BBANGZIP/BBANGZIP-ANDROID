package org.android.bbangzip.presentation.ui.profileedit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProfileEditRoute(
    navigateToMy: () -> Unit,
    viewModel: ProfileEditViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileEditScreen(
        state = state,
        onBackIconClick = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnBackIconClick)
        },
        onProfileImageClick = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnProfileImgClick)
        },
        onNicknameChange = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnNicknameChange(it))
        },
        onNicknameClick = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnNicknameClick)
        },
        onCommitmentAreaClick = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnCommitmentMessageClick)
        },
        onNicknameInputBottomSheetDismissRequest = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnNicknameBottomSheetDismissRequest)
        },
        onNicknameInputDoneAction = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnNicknameBottomSheetDismissRequest)
        },
        onProfileImageBottomSheetDismissRequest = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnProfileImgBottomSheetDismissRequest)
        },
        onSelectProfileImg = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnProfileImgSelect(it))
        },
        onProfileImageCancelBtnClick = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnProfileImgBottomSheetDismissRequest)
        },
        onProfileImageCompleteBtnClick = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnProfileImgCompleteBtnClick)
        },
        onCommitmentBottomSheetDismissRequest = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnCommitmentBottomSheetDismissRequest)
        },
        onCommitmentMessageChange = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnCommitmentMessageChange(it))
        },
    )
}
