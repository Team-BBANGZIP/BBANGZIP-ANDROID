package org.android.bbangzip.presentation.type

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import org.android.bbangzip.R
import org.android.bbangzip.ui.theme.defaultBbangZipColor

enum class TimerViewType(
    @StringRes val titleText: Int? = null,
    val timerFontColor: Color = defaultBbangZipColor.primaryLight_C8B5A2,
    val timerProgressBarColor: Color = defaultBbangZipColor.primaryLight_C8B5A2
) {
    IDLE(
        titleText = R.string.timer_idle_title,
        timerFontColor = defaultBbangZipColor.primaryLight_C8B5A2,
    ),
    RUNNING(
        titleText = null,
        timerFontColor = defaultBbangZipColor.primaryNormal_897869,
    ),
    PAUSED(
        titleText = R.string.timer_paused_title,
        timerFontColor = defaultBbangZipColor.primaryNormal_897869,
    ),
    COMPLETE(
        titleText = R.string.timer_complete_title,
        timerFontColor = defaultBbangZipColor.primaryStrong_4B4137,
    )
}