package org.android.bbangzip.presentation.ui.timer.contract.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class TimerBottomSheetVisibleState(
    val breadSelection: Boolean = false,
    val restart: Boolean = false,
    val reset: Boolean = false,
    val complete: Boolean = false,
) : Parcelable
