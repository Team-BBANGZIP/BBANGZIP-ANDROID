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

                // 닉네임
                OnboardingContract.OnboardingEvent.OnClickNicknameTextField -> {
                    updateState(OnboardingContract.OnboardingReduce.UpdateNicknameBottomSheetVisibility(isVisible = true))
                }

                is OnboardingContract.OnboardingEvent.OnChangeNickname -> {
                    updateState(OnboardingContract.OnboardingReduce.UpdateNickname(event.input))
                }

                OnboardingContract.OnboardingEvent.OnClickNicknameBottomSheetDismissRequest -> {
                    setSideEffect(OnboardingContract.OnboardingSideEffect.DismissNicknameInputBottomSheet)
                }

                OnboardingContract.OnboardingEvent.OnNicknameInputDone -> {
                    val finalNickname = currentUiState.nickname

                    if (finalNickname.isNotEmpty()) {
                        updateState(OnboardingContract.OnboardingReduce.UpdateSaveButtonEnabled(true))
                    } else {
                        updateState(OnboardingContract.OnboardingReduce.UpdateSaveButtonEnabled(false))
                    }

                    setSideEffect(OnboardingContract.OnboardingSideEffect.DismissNicknameInputBottomSheet)
                }

                // 프로필 이미지
                OnboardingContract.OnboardingEvent.OnClickProfileImgSettingBtn -> {
                    updateState(OnboardingContract.OnboardingReduce.UpdateProfileImgBottomSheetVisibility(true))
                    updateState(OnboardingContract.OnboardingReduce.UpdateSelectedProfileImg(currentUiState.profileImg))
                }

                is OnboardingContract.OnboardingEvent.OnSelectProfileImg -> {
                    updateState(OnboardingContract.OnboardingReduce.UpdateSelectedProfileImg(event.imgResId))
                }

                OnboardingContract.OnboardingEvent.OnClickProfileImgCancelBtn,
                OnboardingContract.OnboardingEvent.OnClickProfileImgBottomSheetDismissRequest -> {
                    setSideEffect(OnboardingContract.OnboardingSideEffect.DismissProfileImgBottomSheet)
                    updateState(OnboardingContract.OnboardingReduce.UpdateSelectedProfileImg(currentUiState.profileImg))
                }

                OnboardingContract.OnboardingEvent.OnClickProfileImgCompleteBtn -> {
                    updateState(OnboardingContract.OnboardingReduce.UpdateCurrentProfileImg(currentUiState.selectedImg))
                    setSideEffect(OnboardingContract.OnboardingSideEffect.DismissProfileImgBottomSheet)
                }

                // 온보딩 완료
                OnboardingContract.OnboardingEvent.OnClickSaveBtn -> {
                    if (currentUiState.isSaveBtnEnabled) {
                        setSideEffect(OnboardingContract.OnboardingSideEffect.NavigateToTodo)
                        signup(currentUiState.nickname, currentUiState.profileImg)
                    }
                }
            }
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

                // 저장하기 버튼
                is OnboardingContract.OnboardingReduce.UpdateSaveButtonEnabled -> state.copy(isSaveBtnEnabled = reduce.isEnabled)
            }
        }

    private fun convertResIdToKey(@DrawableRes imgResId: Int): Int {
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

    private fun signup(nickname: String, profileImgKey: Int = 0) {
        viewModelScope.launch {
            userRepository.onboardingComplete(
                onboardingEntity = OnboardingInfo(
                    nickname = nickname,
                    img = convertResIdToKey(profileImgKey)
                )
            ).onSuccess {
                Timber.d("[온보딩] 완료")
            }.onFailure {
                Timber.d("[온보딩] 실패")
            }
        }
    } 
    }
