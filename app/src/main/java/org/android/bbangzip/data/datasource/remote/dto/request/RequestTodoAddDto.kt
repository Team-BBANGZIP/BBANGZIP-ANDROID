package org.android.bbangzip.data.datasource.remote.dto.request

import java.time.LocalDate
import java.time.LocalTime

data class RequestTodoAddDto(
    val categoryId: Long,
    val content: String,
    val targetDate: LocalDate,
    val startTime: LocalTime?,
)
