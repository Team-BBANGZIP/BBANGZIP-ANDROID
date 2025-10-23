package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.Serializable
import org.android.bbangzip.data.datasource.remote.util.serializer.LocalTimeSerializer
import org.android.bbangzip.domain.model.Todo
import java.time.LocalTime

@Serializable
data class ResponsePatchTodoTimeDto(
    val todoId: Long,
    @Serializable(with = LocalTimeSerializer::class)
    val startTime: LocalTime?,
) {
    // Todo nullable하게 만들기
    fun toTodo() =
        Todo(
            todoId = todoId.toInt(),
            content = "",
            isCompleted = false,
            startTime = startTime,
        )
}
