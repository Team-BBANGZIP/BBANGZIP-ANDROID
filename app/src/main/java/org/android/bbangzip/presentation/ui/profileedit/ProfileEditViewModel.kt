package org.android.bbangzip.presentation.ui.profileedit

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.bbangzip.domain.repository.UserRepository
import org.android.bbangzip.presentation.common.base.BaseViewModel
import org.android.bbangzip.presentation.common.util.constant.OnboardingConstants
import org.android.bbangzip.presentation.common.util.constant.OnboardingConstants.DEFAULT_PROFILE_IMG_RES_ID
import org.android.bbangzip.presentation.ui.profileedit.ProfileEditContract.ProfileEditReduce.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
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
                    loadInitialData()
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

        private fun convertResIdToKey(
            @DrawableRes imgResId: Int,
        ): Int {
            return when (imgResId) {
                DEFAULT_PROFILE_IMG_RES_ID -> 0

                else -> {
                    val index = OnboardingConstants.PROFILE_IMG_RES_IDS.indexOf(imgResId)
                    if (index != -1) {
                        index + 1
                    } else {
                        0
                    }
                }
            }
        }

        private fun loadInitialData() {
            viewModelScope.launch {
                userRepository.getProfileInformation()
                    .onSuccess { profileInformation ->

                    }.onFailure {
                        Timber.e(it, "[마이페이지] 프로필 정보 불러오기 실패")
                    }
            }
        }

        private fun modifyProfile() {
            viewModelScope.launch {
                userRepository.modifyProfileInformation(
                    profileImageKey = convertResIdToKey(currentUiState.selectedImg),
                    nickname = currentUiState.nickname,
                    commitmentMessage = currentUiState.commitmentMessage,
                ).onSuccess {
                }.onFailure {
                    Timber.e(it,"[마이페이지] 프로필 정보 수정 실패")
                }
            }
        }
    }
