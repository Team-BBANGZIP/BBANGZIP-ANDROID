package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.TodoCount

@Serializable
data class ResponseDeleteTodoDto(
    val completedCount: Int,
    val totalCount: Int
){
    fun toTodoCount() =
        TodoCount(
            completedCount = completedCount,
            totalCount = totalCount
        )
}
