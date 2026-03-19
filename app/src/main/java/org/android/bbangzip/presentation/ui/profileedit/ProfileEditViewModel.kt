package org.android.bbangzip.presentation.ui.profileedit

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.bbangzip.presentation.common.base.BaseViewModel
import org.android.bbangzip.presentation.ui.profileedit.ProfileEditContract.ProfileEditReduce.*
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : BaseViewModel<ProfileEditContract.ProfileEditEvent, ProfileEditContract.ProfileEditState, ProfileEditContract.ProfileEditReduce, ProfileEditContract.ProfileEditSideEffect>(
            savedStateHandle = savedStateHandle,
        ) {
        override fun createInitialState(savedState: Parcelable?): ProfileEditContract.ProfileEditState {
            return savedState as? ProfileEditContract.ProfileEditState ?: ProfileEditContract.ProfileEditState()
        }

        override fun handleEvent(event: ProfileEditContract.ProfileEditEvent) {
            when (event) {
                ProfileEditContract.ProfileEditEvent.Initialize -> {
                }

                ProfileEditContract.ProfileEditEvent.OnBackIconClick -> {
                    setSideEffect(ProfileEditContract.ProfileEditSideEffect.NavigateToBack)
                }

                is ProfileEditContract.ProfileEditEvent.OnCommitmentMessageChange -> {
                    updateState(UpdateState(currentUiState.copy(commitmentMessage = event.commitmentMessage)))
                }

                ProfileEditContract.ProfileEditEvent.OnCommitmentMessageClick -> {
                    updateState(UpdateState(currentUiState.copy(isCommitmentBottomSheetVisible = true)))
                }

                is ProfileEditContract.ProfileEditEvent.OnNicknameChange -> {
                    updateState(UpdateState(currentUiState.copy(nickname = event.nickname)))
                }

                ProfileEditContract.ProfileEditEvent.OnNicknameClick -> {
                    updateState(UpdateState(currentUiState.copy(isNicknameBottomSheetVisible = true)))
                }

                ProfileEditContract.ProfileEditEvent.OnProfileImgClick -> {
                    updateState(UpdateState(currentUiState.copy(isProfileImgBottomSheetVisible = true)))
                }

                is ProfileEditContract.ProfileEditEvent.OnProfileImgSelect -> {
                    updateState(UpdateState(currentUiState.copy(selectedImg = event.imgRes)))
                }

                ProfileEditContract.ProfileEditEvent.OnNicknameBottomSheetDismissRequest -> {
                    updateState(UpdateState(currentUiState.copy(isNicknameBottomSheetVisible = false)))
                }

                ProfileEditContract.ProfileEditEvent.OnProfileImgBottomSheetDismissRequest -> {
                    updateState(UpdateState(currentUiState.copy(isProfileImgBottomSheetVisible = false)))
                }

                ProfileEditContract.ProfileEditEvent.OnCommitmentBottomSheetDismissRequest -> {
                    updateState(UpdateState(currentUiState.copy(isCommitmentBottomSheetVisible = false)))
                }

                ProfileEditContract.ProfileEditEvent.OnProfileImgCompleteBtnClick -> {
                    updateState(
                        UpdateState(
                            currentUiState.copy(
                                profileImg = currentUiState.selectedImg,
                                isProfileImgBottomSheetVisible = false,
                            ),
                        ),
                    )
                }
            }
        }

        override fun reduceState(
            state: ProfileEditContract.ProfileEditState,
            reduce: ProfileEditContract.ProfileEditReduce,
        ): ProfileEditContract.ProfileEditState {
            return when (reduce) {
                is ProfileEditContract.ProfileEditReduce.UpdateState -> reduce.state
            }
        }
    }
