package org.android.bbangzip.presentation.ui.my

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.base.BaseContract

class MyContract {
    @Parcelize
    data class MyState(
        val profileImgRes: Int = R.drawable.ic_profile_default_100,
        val nickname: String = "",
        val commitmentMessage: String = "다짐 메세지를 입력해 주세요.",
        val appVersion: String = "",
        val isLogoutConfirmBottomSheetVisible: Boolean = false,
        val isWithdrawalConfirmBottomSheetVisible: Boolean = false,
    ) : BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this
    }

    sealed interface MyEvent : BaseContract.Event {
        data object Initialize : MyEvent

        data object OnClickProfileArea : MyEvent

        data object OnClickScreenSetting : MyEvent

        data object OnClickAlarmSetting : MyEvent

        data object OnClickCustomerCenter : MyEvent

        data object OnClickTermsOfService : MyEvent

        data object OnClickFeedback : MyEvent

        data object OnClickReviewApp : MyEvent

        data object OnClickLogoutBtn : MyEvent

        data object OnClickLogoutBottomSheetDismissRequest : MyEvent

        data object OnClickWithdrawalBtn : MyEvent

        data object OnClickWithdrawalBottomSheetDismissRequest : MyEvent

        data object OnConfirmLogout : MyEvent

        data object OnCancelLogoutBottomSheet : MyEvent

        data object OnConfirmWithdrawal : MyEvent

        data object OnCancelWithdrawalBottomSheet : MyEvent
    }

    sealed interface MyReduce : BaseContract.Reduce {
        data class UpdateState(val state: MyState) : MyReduce

        data class UpdateMyAppVersion(val appVersion: String) : MyReduce

        data class UpdateUserInfo(val nickname: String, val commitmentMessage: String, val profileImgRes: Int) : MyReduce

        data class UpdateLogoutBottomSheetVisibility(val isVisible: Boolean) : MyReduce

        data class UpdateWithdrawalBottomSheetVisibility(val isVisible: Boolean) : MyReduce
    }

    sealed interface MySideEffect : BaseContract.SideEffect {
        data object NavigateToProfileEdit : MySideEffect

        data object NavigateToScreenSetting : MySideEffect

        data object OpenCustomerCenterWeb : MySideEffect

        data object OpenTermsOfServiceWeb : MySideEffect

        data object OpenFeedbackForm : MySideEffect

        data object OpenAppStoreReview : MySideEffect

        data object NavigateToLogin : MySideEffect
    }
}
