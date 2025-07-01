package org.android.bbangzip.presentation.model

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.type.TimerViewType

@Parcelize
sealed class TimerState: Parcelable {
    data object Idle : TimerState()
    data object Running : TimerState()
    data object Paused : TimerState()
    data object Complete : TimerState()
}

val stateToTypeMap = mapOf(
    TimerState.Idle to TimerViewType.IDLE,
    TimerState.Running to TimerViewType.RUNNING,
    TimerState.Paused to TimerViewType.PAUSED,
    TimerState.Complete to TimerViewType.COMPLETE
)

fun TimerState.getTitleText() : Int = stateToTypeMap[this]?.titleText ?: 0

fun TimerState.getTimerFontColor() : Color = stateToTypeMap[this]?.timerFontColor ?: Color.Unspecified

fun TimerState.getTimerProgressBarColor() : Color = stateToTypeMap[this]?.timerProgressBarColor ?: Color.Unspecified