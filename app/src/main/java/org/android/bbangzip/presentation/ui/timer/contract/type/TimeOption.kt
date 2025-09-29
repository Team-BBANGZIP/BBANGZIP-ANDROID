package org.android.bbangzip.presentation.ui.timer.contract.type

import org.android.bbangzip.presentation.ui.timer.contract.model.TimerConstants

enum class TimeOption(val timeOptionIndex: Int, val totalTime: Long, val bredCount: Int) {
    THIRTY(timeOptionIndex = 0, totalTime = TimerConstants.THIRTY_MINUTES, bredCount = 1),
    SIXTY(timeOptionIndex = 1, totalTime = TimerConstants.SIXTY_MINUTES, bredCount = 2)
}