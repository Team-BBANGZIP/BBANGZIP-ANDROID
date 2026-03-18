package org.android.bbangzip.presentation.ui.profileedit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.base.BaseContract

class ProfileEditContract {
    @Parcelize
    data class ProfileEditState(
        val profileImg: Int = R.drawable.ic_profile_default_100,
        val selectedImg: Int = R.drawable.ic_profile_default_100,
        val isProfileImgBottomSheetVisible: Boolean = false,
        val nickname: String = "",
        val isNicknameBottomSheetVisible: Boolean = false,
        val commitmentMessage: String = "",
        val isCommitmentBottomSheetVisible: Boolean = false,
    ) : BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this
    }

    sealed interface ProfileEditEvent : BaseContract.Event {
        data object Initialize : ProfileEditEvent

        data object OnBackIconClick : ProfileEditEvent

        data object OnProfileImgClick : ProfileEditEvent

        data object OnNicknameClick : ProfileEditEvent

        data object OnCommitmentMessageClick : ProfileEditEvent

        data class OnProfileImgSelect(val imgRes: Int) : ProfileEditEvent

        data object OnProfileImgCompleteBtnClick : ProfileEditEvent

        data object OnProfileImgBottomSheetDismissRequest : ProfileEditEvent

        data class OnNicknameChange(val nickname: String) : ProfileEditEvent

        data object OnNicknameBottomSheetDismissRequest : ProfileEditEvent

        data class OnCommitmentMessageChange(val commitmentMessage: String) : ProfileEditEvent

        data object OnCommitmentBottomSheetDismissRequest : ProfileEditEvent
    }

    sealed interface ProfileEditReduce : BaseContract.Reduce {
        data class UpdateState(val state: ProfileEditState) : ProfileEditReduce
    }

    sealed interface ProfileEditSideEffect : BaseContract.SideEffect {
        data object NavigateToBack : ProfileEditSideEffect
    }
}