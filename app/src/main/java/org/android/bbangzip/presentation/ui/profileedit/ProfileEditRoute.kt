package org.android.bbangzip.presentation.ui.profileedit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileEditRoute(
    navigateToBack: () -> Unit,
    viewModel: ProfileEditViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.uiSideEffect) {
        viewModel.uiSideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is ProfileEditContract.ProfileEditSideEffect.NavigateToBack -> navigateToBack()
            }
        }
    }

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
        onCommitmentAreaClick = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnCommitmentMessageClick)
        },
        onProfileImageBottomSheetDismissRequest = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnProfileImgBottomSheetDismissRequest)
        },
        onSelectProfileImage = {
            viewModel.setEvent(ProfileEditContract.ProfileEditEvent.OnProfileImageSelect(it))
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
