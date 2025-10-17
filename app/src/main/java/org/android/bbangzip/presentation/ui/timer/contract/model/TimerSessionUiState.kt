package org.android.bbangzip.presentation.ui.timer.contract.model

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.R
import org.android.bbangzip.ui.theme.defaultBbangZipColor

@Immutable
@Parcelize
sealed interface TimerSessionUiState : Parcelable {
    @Parcelize
    data object Ready : TimerSessionUiState

    @Parcelize
    data object Running : TimerSessionUiState

    @Parcelize
    data object Paused : TimerSessionUiState

    @Parcelize
    data object Complete : TimerSessionUiState
}

@StringRes
fun TimerSessionUiState.getTitleText(): Int {
    return when (this) {
        is TimerSessionUiState.Ready -> R.string.timer_idle_title
        is TimerSessionUiState.Running -> R.string.title_empty_title
        is TimerSessionUiState.Paused -> R.string.timer_paused_title
        is TimerSessionUiState.Complete -> R.string.timer_complete_title
    }
}

fun TimerSessionUiState.getTimerFontColor(): Color {
    return when (this) {
        is TimerSessionUiState.Ready -> defaultBbangZipColor.primaryLight_C8B5A2
        is TimerSessionUiState.Running -> defaultBbangZipColor.primaryNormal_897869
        is TimerSessionUiState.Paused -> defaultBbangZipColor.primaryLight_C8B5A2
        is TimerSessionUiState.Complete -> defaultBbangZipColor.primaryStrong_4B4137
    }
}
