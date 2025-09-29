package org.android.bbangzip.presentation.ui.timer.contract.type

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerConstants

@Parcelize
enum class TimeOption(val timeOptionIndex: Int, val totalTime: Long) : Parcelable {
    THIRTY(timeOptionIndex = 0, totalTime = TimerConstants.THIRTY_MINUTES),
    SIXTY(timeOptionIndex = 1, totalTime = TimerConstants.SIXTY_MINUTES),
    ;

    companion object {
        fun fromIndex(index: Int) = entries.find { it.timeOptionIndex == index } ?: THIRTY
    }
}
