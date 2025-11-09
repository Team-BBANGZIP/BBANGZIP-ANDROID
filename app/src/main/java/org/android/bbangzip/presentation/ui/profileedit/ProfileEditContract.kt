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

    }

    sealed interface ProfileEditReduce : BaseContract.Reduce {
        data class UpdateState(val state: ProfileEditState) : ProfileEditReduce
    }

    sealed interface ProfileEditSideEffect : BaseContract.SideEffect {

    }

}