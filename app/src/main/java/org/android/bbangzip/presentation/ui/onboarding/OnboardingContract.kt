package org.android.bbangzip.presentation.ui.onboarding

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.common.base.BaseContract

class OnboardingContract {
    @Parcelize
    data class OnboardingState(
        val onboardingState: Boolean = false,
    ) : BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this
    }

    sealed interface OnboardingEvent : BaseContract.Event

    sealed interface OnboardingReduce : BaseContract.Reduce

    sealed interface OnboardingSideEffect : BaseContract.SideEffect {
        data object NavigateToTodo : OnboardingSideEffect

        data object NavigateToLogin : OnboardingSideEffect
    }
}
