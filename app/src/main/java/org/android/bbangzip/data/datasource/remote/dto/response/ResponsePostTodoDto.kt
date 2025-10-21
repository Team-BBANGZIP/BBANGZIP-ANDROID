package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.Serializable
import org.android.bbangzip.data.datasource.remote.util.serializer.LocalDateSerializer
import org.android.bbangzip.data.datasource.remote.util.serializer.LocalTimeSerializer
import org.android.bbangzip.domain.model.Todo
import java.time.LocalDate
import java.time.LocalTime

@Serializable
data class ResponsePostTodoDto(
    val todoId: Long,
    val content: String,
    @Serializable(with = LocalDateSerializer::class)
    val targetDate: LocalDate,
    @Serializable(with = LocalTimeSerializer::class)
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
