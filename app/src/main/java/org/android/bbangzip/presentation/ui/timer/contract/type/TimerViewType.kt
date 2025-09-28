package org.android.bbangzip.presentation.ui.timer.contract.type

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import org.android.bbangzip.R
import org.android.bbangzip.ui.theme.defaultBbangZipColor

@Stable
enum class TimerViewType(
    @StringRes val titleText: Int = R.string.timer_idle_title,
    val timerFontColor: Color = defaultBbangZipColor.primaryLight_C8B5A2,
    val timerProgressBarColor: Color = defaultBbangZipColor.primaryLight_C8B5A2,
) {
    IDLE(
        titleText = R.string.timer_idle_title,
        timerFontColor = defaultBbangZipColor.primaryLight_C8B5A2,
    ),
    RUNNING(
        titleText = R.string.title_empty_title,
        timerFontColor = defaultBbangZipColor.primaryNormal_897869,
    ),
    PAUSED(
        titleText = R.string.timer_paused_title,
        timerFontColor = defaultBbangZipColor.primaryLight_C8B5A2,
    ),
    COMPLETE(
        titleText = R.string.timer_complete_title,
        timerFontColor = defaultBbangZipColor.primaryStrong_4B4137,
    ),
}
