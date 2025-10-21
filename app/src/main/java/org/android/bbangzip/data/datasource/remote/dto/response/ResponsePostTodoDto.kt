package org.android.bbangzip.data.datasource.remote.dto.response

import org.android.bbangzip.domain.model.Todo
import java.time.LocalDate
import java.time.LocalTime

data class ResponsePostTodoDto(
    val todoId: Long,
    val content: String,
    val targetDate: LocalDate,
    val startTime: LocalTime,
    val isCompleted: Boolean,
    val categoryId: Long,
    val categoryColor: String,
) {
    fun toTodo() =
        Todo(
            todoId = todoId.toInt(),
            content = content,
            startTime = startTime,
            isCompleted = isCompleted,
        )
}
