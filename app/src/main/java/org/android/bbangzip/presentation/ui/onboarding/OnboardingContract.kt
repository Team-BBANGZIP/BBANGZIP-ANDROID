package org.android.bbangzip.presentation.ui.onboarding

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.common.base.BaseContract

class OnboardingContract {
    @Parcelize
    data class OnboardingState(
        val nickname: String = "",
        val isNicknameBottomSheetVisible: Boolean = false,
        val isSaveBtnEnabled: Boolean = false,
        val profileImg: Int? = null,
        val selectedImg: Int? = null,
        val isProfileImgBottomSheetVisible: Boolean = false,
        val onboardingState: Boolean = false,
    ) : BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this
    }

    sealed interface OnboardingEvent : BaseContract.Event {
        data object OnClickPreviousBtn : OnboardingEvent

        data class OnChangeNickname(val input: String) : OnboardingEvent

        data object OnNicknameInputDone : OnboardingEvent

        data object OnClickSaveBtn : OnboardingEvent

        data object OnClickProfileImgSettingBtn : OnboardingEvent

        data object OnClickProfileImgBottomSheetDismissRequest : OnboardingEvent

        data object OnClickProfileImgCancelBtn : OnboardingEvent

        data object OnClickProfileImgCompleteBtn : OnboardingEvent

        data class OnSelectProfileImg(val imgResId: Int) : OnboardingEvent
    }

    sealed interface OnboardingReduce : BaseContract.Reduce {
        data class UpdateState(val state: OnboardingState) : OnboardingReduce

        data class UpdateNickname(val nickname: String) : OnboardingReduce

        data class UpdateNicknameBottomSheetVisibility(val isVisible: Boolean) : OnboardingReduce

        data class UpdateSaveButtonEnabled(val isEnabled: Boolean) : OnboardingReduce

        data class UpdateProfileImgBottomSheetVisibility(val isVisible: Boolean) : OnboardingReduce

        data class UpdateCurrentProfileImg(val imgResId: Int) : OnboardingReduce

        data class UpdateSelectedProfileImg(val imgResId: Int?) : OnboardingReduce
    }

    sealed interface OnboardingSideEffect : BaseContract.SideEffect {
        data object NavigateToTimer : OnboardingSideEffect

        data object NavigateToLogin : OnboardingSideEffect

        data object DismissProfileImgBottomSheet : OnboardingSideEffect
    }
}
