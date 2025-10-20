package org.android.bbangzip.domain.model

data class TodoCompletionInfo(
    val todoId: Long,
    val isCompleted: Boolean,
    val completionCount: Int,
    val totalTodoCount: Int
)