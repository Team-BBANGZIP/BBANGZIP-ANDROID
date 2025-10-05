package org.android.bbangzip.presentation.ui.timer.contract.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.domain.model.BreadInfo

@Parcelize
data class BreadInfoUiState(
    val id: Int,
    val name: String,
    val isUnLocked: Boolean,
    val requiredCount: Int,
) : Parcelable

fun BreadInfo.toUiState(): BreadInfoUiState =
    BreadInfoUiState(
        id = this.breadId.toInt(),
        name = this.breadName,
        isUnLocked = this.isUnlocked,
        requiredCount = this.requiredCount,
    )
