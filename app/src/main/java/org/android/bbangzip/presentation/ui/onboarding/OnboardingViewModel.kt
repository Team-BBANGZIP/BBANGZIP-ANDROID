package org.android.bbangzip.presentation.ui.onboarding

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.bbangzip.presentation.common.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
    @Inject
    constructor(
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
    }
