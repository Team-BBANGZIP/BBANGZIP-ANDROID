package org.android.bbangzip.data.datasource.remote.dto.response

import java.time.LocalTime

data class ResponsePatchTodoTimeDto(
    val todoId: Long,
    val startTime: LocalTime?
)
