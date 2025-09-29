package org.android.bbangzip.presentation.ui.timer.contract.model

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.R
import org.android.bbangzip.ui.theme.defaultBbangZipColor

@Parcelize
sealed interface TimerSessionUiState : Parcelable {
    data class Ready(
        val breadList: List<BreadInfoUiState> =
            listOf(
                BreadInfoUiState(1, "소금빵", false, 0),
                BreadInfoUiState(2, "식빵", false, 5),
                BreadInfoUiState(3, "바게트", true, 10),
                BreadInfoUiState(4, "크루아상", true, 15),
                BreadInfoUiState(5, "모닝빵", true, 20),
                BreadInfoUiState(6, "바게트", true, 10),
                BreadInfoUiState(7, "크루아상", true, 15),
                BreadInfoUiState(8, "모닝빵", true, 20),
                BreadInfoUiState(9, "모닝빵", true, 20),
            ),
        val todayBreadCount: Int = 0,
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

/**
 * TimerSessionUiState에 따라 타이머 텍스트의 색상을 반환합니다.
 */
fun TimerSessionUiState.getTimerFontColor(): Color {
    return when (this) {
        is TimerSessionUiState.Ready -> defaultBbangZipColor.primaryLight_C8B5A2
        is TimerSessionUiState.Running -> defaultBbangZipColor.primaryNormal_897869
        is TimerSessionUiState.Paused -> defaultBbangZipColor.primaryLight_C8B5A2
        is TimerSessionUiState.Complete -> defaultBbangZipColor.primaryStrong_4B4137
    }
}
