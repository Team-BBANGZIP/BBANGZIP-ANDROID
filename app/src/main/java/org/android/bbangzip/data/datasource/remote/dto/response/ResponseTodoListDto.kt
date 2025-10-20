package org.android.bbangzip.data.datasource.remote.dto.response

import org.android.bbangzip.domain.model.CategoryInfo
import org.android.bbangzip.domain.model.TodoInfo
import org.android.bbangzip.domain.model.TodoListInfo
import org.android.bbangzip.domain.model.TodoSummaryInfo
import java.time.LocalDate
import java.time.LocalTime

data class ResponseTodoListDto(
    val commitmentMessage: String,
    val todoSummary: TodoSummaryDto,
    val categories: List<CategoryDto>
){
    fun toTodoListInfo() =
        TodoListInfo(
            commitmentMessage = commitmentMessage,
            todoSummary = todoSummary.toTodoSummaryInfo(),
            categories = categories.map { it.toCategoryInfo() }
        )
}

data class TodoSummaryDto(
    val date: LocalDate,
    val totalCount: Int,
    val completedCount: Int
){
    fun toTodoSummaryInfo() =
        TodoSummaryInfo(
            date = date,
            totalCount = totalCount,
            completedCount = completedCount
        )
}

data class CategoryDto(
    val categoryId: Int,
    val categoryName: String,
    val categoryColor: String,
    val todos: List<TodoDto>
){
    fun toCategoryInfo() =
        CategoryInfo(
            categoryId = categoryId,
            categoryName = categoryName,
            categoryColor = categoryColor,
            todos = todos.map { it.toTodoInfo() }
        )
}

data class TodoDto(
    val todoId: Int,
    val content: String,
    val isCompleted: Boolean,
    val startTime: LocalTime?
){
    fun toTodoInfo() = TodoInfo(
        todoId = todoId,
        content = content,
        isCompleted = isCompleted,
        startTime = startTime
    )
}