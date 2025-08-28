package org.android.bbangzip.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val categoryId: Int,
    val categoryName: String,
    val categoryColor: String,
    val todos: List<Todo>
): Parcelable
