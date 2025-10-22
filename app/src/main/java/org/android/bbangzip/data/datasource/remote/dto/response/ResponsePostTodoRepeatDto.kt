package org.android.bbangzip.data.datasource.remote.dto.response

import java.time.LocalDate
import java.time.LocalTime

data class ResponsePostTodoRepeatDto(
    val todoId: Long,
    val content: String,
    val targetDate: LocalDate,
    val startTime: LocalTime?, // string으로 주는데 물어보기
    val isCompleted: Boolean,
)
