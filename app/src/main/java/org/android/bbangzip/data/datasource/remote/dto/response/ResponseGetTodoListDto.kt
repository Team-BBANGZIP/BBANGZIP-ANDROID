package org.android.bbangzip.data.datasource.remote.dto.response

import org.android.bbangzip.domain.model.Category
import org.android.bbangzip.domain.model.Todo
import org.android.bbangzip.domain.model.TodoList
import org.android.bbangzip.domain.model.TodoSummary
import java.time.LocalDate
import java.time.LocalTime

data class ResponseGetTodoListDto(
    val commitmentMessage: String,
    val todoSummary: TodoSummaryDto,
    val categories: List<CategoryDto>,
) {
    fun toTodoList() =
        TodoList(
            commitmentMessage = commitmentMessage,
            todoSummary = todoSummary.toTodoSummary(),
            categories = categories.map { it.toCategory() },
        )
}

data class TodoSummaryDto(
    val date: LocalDate,
    val totalCount: Int,
    val completedCount: Int,
) {
    fun toTodoSummary() =
        TodoSummary(
            date = date,
            totalCount = totalCount,
            completedCount = completedCount,
        )
}

data class CategoryDto(
    val categoryId: Int,
    val categoryName: String,
    val categoryColor: String,
    val todos: List<TodoDto>,
) {
    fun toCategory() =
        Category(
            categoryId = categoryId,
            categoryName = categoryName,
            categoryColor = categoryColor,
            todos = todos.map { it.toTodo() },
        )
}

data class TodoDto(
    val todoId: Int,
    val content: String,
    val isCompleted: Boolean,
    val startTime: LocalTime?,
) {
    fun toTodo() =
        Todo(
            todoId = todoId,
            content = content,
            isCompleted = isCompleted,
            startTime = startTime,
        )
}
