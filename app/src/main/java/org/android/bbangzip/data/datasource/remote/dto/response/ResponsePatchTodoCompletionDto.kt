package org.android.bbangzip.data.datasource.remote.dto.response

import org.android.bbangzip.domain.model.TodoCompletionInfo

data class ResponsePatchTodoCompletionDto(
    val todoId: Long,
    val isCompleted: Boolean,
    val completedCount: Int,
    val totalCount: Int,
) {
    fun toTodoCompletionInfo() =
        TodoCompletionInfo(
            todoId = todoId,
            isCompleted = isCompleted,
            completionCount = completedCount,
            totalTodoCount = totalCount,
        )
}
