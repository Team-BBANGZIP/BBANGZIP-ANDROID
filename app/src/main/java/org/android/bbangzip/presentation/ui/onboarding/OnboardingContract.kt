package org.android.bbangzip.presentation.ui.onboarding

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.base.BaseContract

class OnboardingContract {
    @Parcelize
    data class OnboardingState(
        val nickname: String = "",
        val isNicknameValid: Boolean = false,
        val isNicknameBottomSheetVisible: Boolean = false,

        val profileImg: Int = R.drawable.ic_profile_default_100,
        val selectedImg: Int = R.drawable.ic_profile_default_100,
        val isProfileImgBottomSheetVisible: Boolean = false,

        val isSaveBtnEnabled: Boolean = false,

        val onboardingState: Boolean = false,
    ) : BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this
    }

    sealed interface OnboardingEvent : BaseContract.Event {
        data object Initialize : OnboardingEvent
        data object OnClickPreviousBtn : OnboardingEvent

        data object OnClickNicknameTextField : OnboardingEvent
        data class OnInputNickname(val input: String) : OnboardingEvent
        data object OnClickNicknameInputDone : OnboardingEvent

        data object OnClickProfileImgSettingBtn : OnboardingEvent
        data class OnSelectProfileImg(val index: Int) : OnboardingEvent
        data object OnClickProfileImgCancelBtn : OnboardingEvent
        data object OnClickProfileImgCompleteBtn : OnboardingEvent

        data object OnClickSaveBtn : OnboardingEvent
    }

    sealed interface OnboardingReduce : BaseContract.Reduce {
        data class UpdateNicknameInput(val input: String, val isValid: Boolean) : OnboardingReduce
        data class UpdateNickname(val nickname: String, val isValid: Boolean) : OnboardingReduce
        data class UpdateNicknameBottomSheetVisibility(val isVisible: Boolean) : OnboardingReduce

        data class UpdateProfileImgBottomSheetVisibility(val isVisible: Boolean) : OnboardingReduce
        data class UpdateSelectedProfileIndex(val index: Int) : OnboardingReduce
        data class UpdateProfileImg(val imgResId: Int) : OnboardingReduce

        data class UpdateSaveButtonEnabled(val isEnabled: Boolean) : OnboardingReduce
        data class UpdateOnboardingCompleted(val isCompleted: Boolean) : OnboardingReduce
    }

    sealed interface OnboardingSideEffect : BaseContract.SideEffect {
        data object NavigateToTodo : OnboardingSideEffect
        data object NavigateToLogin : OnboardingSideEffect

        data object DismissProfileImgBottomSheet : OnboardingSideEffect
        data object DismissNicknameInputBottomSheet : OnboardingSideEffect
        data class TriggerNicknameValidation(val currentInput: String) : OnboardingSideEffect
    }
}
