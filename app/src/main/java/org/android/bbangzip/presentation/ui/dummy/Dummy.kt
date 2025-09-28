package org.android.bbangzip.presentation.ui.dummy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dummy(
    val dummyA: String,
    val dummyB: String,
) : Parcelable
