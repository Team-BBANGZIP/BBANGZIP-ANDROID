package org.android.bbangzip.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import org.android.bbangzip.presentation.common.model.serializer.LocalTimeSerializer
import java.time.LocalTime

@Serializable
@Parcelize
data class Todo(
    val todoId: Int,
    val content: String,
    val isCompleted: Boolean,
    @Serializable(with = LocalTimeSerializer::class)
    val startTime: LocalTime? = null,
) : Parcelable