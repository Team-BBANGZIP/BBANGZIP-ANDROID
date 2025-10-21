package org.android.bbangzip.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class NewTodo(
    val todoId: Long,
    val content: String,
    val targetDate: LocalDate,
    val startTime: LocalTime,
    val isCompleted: Boolean,
    val categoryId: Long,
    val categoryColor: String,
)
