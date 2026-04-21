package org.android.bbangzip.presentation.ui.onboarding

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.bbangzip.domain.model.OnboardingInfo
import org.android.bbangzip.domain.repository.UserRepository
import org.android.bbangzip.presentation.common.base.BaseViewModel
import org.android.bbangzip.presentation.common.util.constant.OnboardingConstants
import org.android.bbangzip.presentation.common.util.constant.OnboardingConstants.DEFAULT_PROFILE_IMG_RES_ID
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        savedStateHandle: SavedStateHandle,
    ) : BaseViewModel<OnboardingContract.OnboardingEvent, OnboardingContract.OnboardingState, OnboardingContract.OnboardingReduce, OnboardingContract.OnboardingSideEffect>(
            savedStateHandle = savedStateHandle,
        ) {
        override fun createInitialState(savedState: Parcelable?): OnboardingContract.OnboardingState {
            return savedState as? OnboardingContract.OnboardingState ?: OnboardingContract.OnboardingState()
        }

        override fun handleEvent(event: OnboardingContract.OnboardingEvent) {
            when (event) {
                OnboardingContract.OnboardingEvent.OnClickPreviousBtn -> {
                    setSideEffect(OnboardingContract.OnboardingSideEffect.NavigateToLogin)
                }

                is OnboardingContract.OnboardingEvent.OnChangeNickname -> {
                    updateState(OnboardingContract.OnboardingReduce.UpdateNickname(event.input))
                }

                OnboardingContract.OnboardingEvent.OnClickProfileImgSettingBtn -> {
                    updateState(OnboardingContract.OnboardingReduce.UpdateProfileImgBottomSheetVisibility(true))
                    currentUiState.profileImg?.let { updateState(OnboardingContract.OnboardingReduce.UpdateSelectedProfileImg(it)) }
                }

                is OnboardingContract.OnboardingEvent.OnSelectProfileImg -> {
                    updateState(OnboardingContract.OnboardingReduce.UpdateSelectedProfileImg(event.imgResId))
                }

                OnboardingContract.OnboardingEvent.OnClickProfileImgCancelBtn -> {
                    updateState(OnboardingContract.OnboardingReduce.UpdateProfileImgBottomSheetVisibility(isVisible = false))
                    updateState(OnboardingContract.OnboardingReduce.UpdateSelectedProfileImg(currentUiState.profileImg))
                }

                OnboardingContract.OnboardingEvent.OnClickProfileImgBottomSheetDismissRequest -> {
                    applySelectedProfileImgAndDismiss(selectedImg = currentUiState.selectedImg)
                }

                OnboardingContract.OnboardingEvent.OnClickProfileImgCompleteBtn -> {
                    applySelectedProfileImgAndDismiss(selectedImg = currentUiState.selectedImg)
                }

                OnboardingContract.OnboardingEvent.OnClickSaveBtn -> {
                    if (currentUiState.isSaveBtnEnabled) {
                        signup(currentUiState.nickname, currentUiState.profileImg ?: 0)
                    }
                }
            }
        }

        private fun applySelectedProfileImgAndDismiss(
            selectedImg: Int?,
        ) {
            updateState(OnboardingContract.OnboardingReduce.UpdateProfileImgBottomSheetVisibility(isVisible = false))
            selectedImg?.let { updateState(OnboardingContract.OnboardingReduce.UpdateCurrentProfileImg(it)) }
        }

        override fun reduceState(
            state: OnboardingContract.OnboardingState,
            reduce: OnboardingContract.OnboardingReduce,
        ): OnboardingContract.OnboardingState {
            return when (reduce) {
                is OnboardingContract.OnboardingReduce.UpdateState -> reduce.state

                // 닉네임
                is OnboardingContract.OnboardingReduce.UpdateNickname -> state.copy(nickname = reduce.nickname)
                is OnboardingContract.OnboardingReduce.UpdateNicknameBottomSheetVisibility -> state.copy(isNicknameBottomSheetVisible = reduce.isVisible)

                // 프로필 이미지
                is OnboardingContract.OnboardingReduce.UpdateCurrentProfileImg -> state.copy(profileImg = reduce.imgResId)
                is OnboardingContract.OnboardingReduce.UpdateProfileImgBottomSheetVisibility -> state.copy(isProfileImgBottomSheetVisible = reduce.isVisible)
                is OnboardingContract.OnboardingReduce.UpdateSelectedProfileImg -> state.copy(selectedImg = reduce.imgResId)
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

        private fun signup(
            nickname: String,
            profileImgKey: Int,
        ) {
            viewModelScope.launch {
                userRepository.onboardingComplete(
                    onboardingEntity =
                        OnboardingInfo(
                            nickname = nickname,
                            img = convertResIdToKey(profileImgKey),
                        ),
                ).onSuccess {
                    Timber.d("[온보딩] 완료")
                    setSideEffect(OnboardingContract.OnboardingSideEffect.NavigateToTimer)
                }.onFailure {
                    Timber.d("[온보딩] 실패")
                }
            }
        }
    }
