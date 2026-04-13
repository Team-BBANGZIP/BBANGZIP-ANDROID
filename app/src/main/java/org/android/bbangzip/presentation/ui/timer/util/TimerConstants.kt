package org.android.bbangzip.presentation.ui.timer.util

import org.android.bbangzip.BuildConfig

object TimerConstants {
    val THIRTY_MINUTES: Long = if (BuildConfig.DEBUG) 10_000L else 1_800_000L
    val SIXTY_MINUTES: Long = if (BuildConfig.DEBUG) 20_000L else 3_600_000L
}
