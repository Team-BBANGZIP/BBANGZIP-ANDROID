package org.android.bbangzip.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class TodoListInfo(
    val commitmentMessage: String,
    val todoSummary: TodoSummaryInfo,
    val categories: List<CategoryInfo>
)

data class TodoSummaryInfo(
    val date: LocalDate,
    val totalCount: Int,
    val completedCount: Int
)

data class CategoryInfo(
    val categoryId: Int,
    val categoryName: String,
    val categoryColor: String,
    val todos: List<TodoInfo>
)

data class TodoInfo(
    val todoId: Int,
    val content: String,
    val isCompleted: Boolean,
    val startTime: LocalTime?
)