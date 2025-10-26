package org.android.bbangzip.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class TodoList(
    val commitmentMessage: String,
    val todoSummary: TodoSummary,
    val categories: List<Category>,
)

data class TodoSummary(
    val date: LocalDate,
    val totalCount: Int,
    val completedCount: Int,
)

data class Category(
    val categoryId: Int,
    val categoryName: String,
    val categoryColor: String,
    val isStopped: Boolean = false,
    val todos: List<Todo>,
)

data class Todo(
    val todoId: Int,
    val content: String,
    val isCompleted: Boolean,
    val startTime: LocalTime?,
)
