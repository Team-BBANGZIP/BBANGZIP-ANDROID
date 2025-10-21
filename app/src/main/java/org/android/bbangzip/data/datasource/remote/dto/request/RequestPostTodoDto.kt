package org.android.bbangzip.data.datasource.remote.dto.request

import java.time.LocalDate
import java.time.LocalTime

data class RequestPostTodoDto(
    val categoryId: Long,
    val content: String,
    val targetDate: LocalDate,
    val startTime: LocalTime?,
)
