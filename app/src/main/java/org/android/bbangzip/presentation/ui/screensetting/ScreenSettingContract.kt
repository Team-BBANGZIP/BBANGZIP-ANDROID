package org.android.bbangzip.presentation.ui.screensetting

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.common.base.BaseContract

class ScreenSettingContract {
    @Parcelize
    data class ScreenSettingState(
        val isSundayStartEnabled: Boolean = false,
    ) : BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this
    }

    sealed interface ScreenSettingEvent : BaseContract.Event {
        data object Initialize : ScreenSettingEvent

        data object OnBackIconClick : ScreenSettingEvent

        data object OnSundayStartToggle : ScreenSettingEvent
    }

    sealed interface ScreenSettingReduce : BaseContract.Reduce {
        data class UpdateSundayStartEnabled(val isEnabled: Boolean) : ScreenSettingReduce
    }

    sealed interface ScreenSettingSideEffect : BaseContract.SideEffect {
        data object NavigateToBack : ScreenSettingSideEffect
    }
}
