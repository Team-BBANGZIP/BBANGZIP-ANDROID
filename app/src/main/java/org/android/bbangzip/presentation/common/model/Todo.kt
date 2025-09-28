package org.android.bbangzip.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class Todo(
    val todoId: Int,
    val content: String,
    val isCompleted: Boolean,
    val startTime: LocalTime? = null,
) : Parcelable
