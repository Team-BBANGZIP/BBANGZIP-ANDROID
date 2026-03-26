package org.android.bbangzip.presentation.ui.auth

import android.content.Context
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.common.base.BaseContract

class LoginContract {
    @Parcelize
    data class LoginState(
        val isLoading: Boolean = false,
        val isOnboardingCompleted: Boolean = false,
        val loginState: Boolean = false,
    ) : BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this
    }

    sealed interface LoginEvent : BaseContract.Event {
        data class OnClickKakaoLoginBtn(val context: Context) : LoginEvent
    }

    sealed interface LoginReduce : BaseContract.Reduce {
        data class UpdateState(val state: LoginState) : LoginReduce

        data class UpdateLoginSuccess(val success: Boolean) : LoginReduce
    }

    sealed interface LoginSideEffect : BaseContract.SideEffect {
        data object NavigateToOnboarding : LoginSideEffect

        data object NavigateToTimer : LoginSideEffect
    }
}
