package org.android.bbangzip.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BreadInfo(
    val id: Int,
    val name: String,
    val isLocked: Boolean,
    val requiredCount: Int,
) : Parcelable
