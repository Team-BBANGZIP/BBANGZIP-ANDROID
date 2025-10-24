package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.Category

@Serializable
data class ResponseCategoryDto(
    val categoryId: Long,
    val name: String,
    val color: String,
    val isStopped: Boolean,
){
    fun toCategory(): Category =
        Category(
            categoryId = categoryId.toInt(),
            categoryName = name,
            categoryColor = color,
            isStopped = isStopped,
            todos = emptyList()
        )
}
