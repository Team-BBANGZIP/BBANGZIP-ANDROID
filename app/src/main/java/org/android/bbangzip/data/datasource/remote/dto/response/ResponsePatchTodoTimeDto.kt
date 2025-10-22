package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.Serializable
import org.android.bbangzip.data.datasource.remote.util.serializer.LocalTimeSerializer
import java.time.LocalTime

@Serializable
data class ResponsePatchTodoTimeDto(
    val todoId: Long,
    @Serializable(with = LocalTimeSerializer::class)
    val startTime: LocalTime?
)
