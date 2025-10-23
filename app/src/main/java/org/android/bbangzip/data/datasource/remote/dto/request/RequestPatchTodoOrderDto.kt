package org.android.bbangzip.data.datasource.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestPatchTodoReOrderDto(
    val todoId: Long,
    val originCategoryId: Long,
    val targetCategoryId: Long,
    val targetCategoryColor: String,
    val todoList: List<Long>,
)
