package org.android.bbangzip.data.datasource.remote.dto.response

import org.android.bbangzip.domain.model.NewTodo
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
){
    fun toNewTodo() =
        NewTodo(
            todoId = todoId,
            content = content,
            targetDate = targetDate,
            startTime = startTime,
            isCompleted = isCompleted,
            categoryId = categoryId,
            categoryColor = categoryColor
        )
}
