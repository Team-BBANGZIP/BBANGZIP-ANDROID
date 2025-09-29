package org.android.bbangzip.presentation.ui.timer.contract.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BreadInfoUiState(
    val id: Int,
    val name: String,
    val isLocked: Boolean,
    val requiredCount: Int,
) : Parcelable
