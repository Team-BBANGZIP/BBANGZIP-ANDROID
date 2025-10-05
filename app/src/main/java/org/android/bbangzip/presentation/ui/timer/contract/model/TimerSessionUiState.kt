package org.android.bbangzip.presentation.ui.timer.contract.model

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.R
import org.android.bbangzip.ui.theme.defaultBbangZipColor

@Immutable
@Parcelize
sealed interface TimerSessionUiState : Parcelable {
    data class Ready(
        val breadList: ImmutableList<BreadInfoUiState> =
            listOf(
                BreadInfoUiState(1, "소금빵", true, 0),
                BreadInfoUiState(2, "식빵", true, 5),
                BreadInfoUiState(3, "바게트", false, 10),
                BreadInfoUiState(4, "크루아상", false, 15),
                BreadInfoUiState(5, "모닝빵", false, 20),
                BreadInfoUiState(6, "바게트", false, 10),
                BreadInfoUiState(7, "크루아상", false, 15),
                BreadInfoUiState(8, "모닝빵", false, 20),
                BreadInfoUiState(9, "모닝빵", false, 20),
            ).toImmutableList(),
        val totalBreadCount: Int = 0,
    ) : TimerSessionUiState

    data object Running : TimerSessionUiState

    data object Paused : TimerSessionUiState

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
