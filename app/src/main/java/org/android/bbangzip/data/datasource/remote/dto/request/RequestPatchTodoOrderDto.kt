package org.android.bbangzip.data.datasource.remote.dto.request

data class RequestPatchTodoOrderDto(
    val todoId: Long,
    val originCategoryId: Long,
    val targetCategoryId: Long,
    val targetCategoryColor: String,
    val todoList: List<Long>,
)
