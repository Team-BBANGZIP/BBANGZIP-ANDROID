package org.android.bbangzip.presentation.ui.timer.contract.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class TimerSessionUiState : Parcelable {
    data class Ready(
        val breadList: List<BreadInfoUiState>
        = listOf(
            BreadInfoUiState(1, "소금빵", false, 0),
            BreadInfoUiState(2, "식빵", false, 5),
            BreadInfoUiState(3, "바게트", true, 10),
            BreadInfoUiState(4, "크루아상", true, 15),
            BreadInfoUiState(5, "모닝빵", true, 20),
            BreadInfoUiState(6, "바게트", true, 10),
            BreadInfoUiState(7, "크루아상", true, 15),
            BreadInfoUiState(8, "모닝빵", true, 20),
            BreadInfoUiState(9, "모닝빵", true, 20),
        ), val todayBreadCount: Int = 0
    ) : TimerSessionUiState()

    data class Running(val breadLevel: Int = 1) : TimerSessionUiState()

    data class Paused(val breadLevel: Int = 1) : TimerSessionUiState()

    data class Complete(val breadLevel: Int = 4) : TimerSessionUiState()

}