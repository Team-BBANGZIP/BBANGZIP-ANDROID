package org.android.bbangzip.presentation.ui.profileedit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.base.BaseContract
import org.android.bbangzip.presentation.common.util.constant.OnboardingConstants

class ProfileEditContract {
    @Parcelize
    data class ProfileEditState(
        val profileImageResId: Int = R.drawable.ic_profile_default_100,
        val selectedProfileImageKey: Int = 1,
        val isProfileImgBottomSheetVisible: Boolean = false,
        val nickname: String = "",
        val commitmentMessage: String = "",
        val isCommitmentBottomSheetVisible: Boolean = false,
    ) : BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this

        val selectedProfileImageResId get() = OnboardingConstants.PROFILE_IMG_RES_IDS[selectedProfileImageKey-1]
        val isSaveButtonEnabled get() = nickname.isNotEmpty()
    }

    sealed interface ProfileEditEvent : BaseContract.Event {
        data object Initialize : ProfileEditEvent

        data object OnBackIconClick : ProfileEditEvent

        data object OnProfileImgClick : ProfileEditEvent

        data object OnCommitmentMessageClick : ProfileEditEvent

        data class OnProfileImageSelect(val imageRes: Int) : ProfileEditEvent

        data object OnProfileImgCompleteBtnClick : ProfileEditEvent

        data object OnProfileImgBottomSheetDismissRequest : ProfileEditEvent

        data class OnNicknameChange(val nickname: String) : ProfileEditEvent

        data class OnCommitmentMessageChange(val commitmentMessage: String) : ProfileEditEvent

        data object OnCommitmentBottomSheetDismissRequest : ProfileEditEvent

        data object OnSaveButtonClick : ProfileEditEvent
    }

    sealed interface ProfileEditReduce : BaseContract.Reduce {
        data class UpdateState(val state: ProfileEditState) : ProfileEditReduce
    }

    sealed interface ProfileEditSideEffect : BaseContract.SideEffect {
        data object NavigateToBack : ProfileEditSideEffect
    }
}
