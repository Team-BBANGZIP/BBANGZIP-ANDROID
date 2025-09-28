package org.android.bbangzip.presentation.ui.timer.contract.model

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.ui.timer.contract.type.TimerViewType

@Parcelize
sealed class TimerStatus : Parcelable {
    data object Idle : TimerStatus()

    data object Running : TimerStatus()

    data object Paused : TimerStatus()

    data object Complete : TimerStatus()
}

val stateToTypeMap =
    mapOf(
        TimerStatus.Idle to TimerViewType.IDLE,
        TimerStatus.Running to TimerViewType.RUNNING,
        TimerStatus.Paused to TimerViewType.PAUSED,
        TimerStatus.Complete to TimerViewType.COMPLETE,
    )

fun TimerStatus.getTitleText(): Int = stateToTypeMap[this]?.titleText ?: 0

fun TimerStatus.getTimerFontColor(): Color = stateToTypeMap[this]?.timerFontColor ?: Color.Unspecified

fun TimerStatus.getTimerProgressBarColor(): Color = stateToTypeMap[this]?.timerProgressBarColor ?: Color.Unspecified
