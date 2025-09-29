package org.android.bbangzip.presentation.ui.timer.contract.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimerBottomSheetVisibleState(
    val breadSelection: Boolean = false,
    val restart: Boolean = false,
    val reset: Boolean = false,
    val complete: Boolean = false,
) : Parcelable