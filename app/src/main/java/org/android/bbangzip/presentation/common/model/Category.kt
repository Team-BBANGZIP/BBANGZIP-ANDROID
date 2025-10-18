package org.android.bbangzip.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val color: String,
    val isStopped: Boolean = false,
    val todos: List<Todo> = emptyList(),
) : Parcelable
