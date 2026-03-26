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

        data object NavigateToLogin : MySideEffect

        sealed class OpenExternalUrl(val url: String) : MySideEffect {
            data class AppStoreReview(val packageName: String) : OpenExternalUrl("https://play.google.com/store/apps/details?id=$packageName")

            data object CustomerCenter : OpenExternalUrl("https://southern-comet-4a3.notion.site/2ac01508929380518f17feaa3aa64870?source=copy_link")

            data object Instagram : OpenExternalUrl("https://www.instagram.com/bbangzip.official/")

            data object FeedbackForm : OpenExternalUrl("https://southern-comet-4a3.notion.site/2ac01508929380518f17feaa3aa64870?source=copy_link")
        }
    }
}
