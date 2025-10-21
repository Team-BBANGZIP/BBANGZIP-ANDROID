package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.TodoCompletionInfo

@Serializable
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
