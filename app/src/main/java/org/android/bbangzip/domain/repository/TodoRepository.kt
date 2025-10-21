package org.android.bbangzip.domain.repository

import org.android.bbangzip.domain.model.NewTodo
import org.android.bbangzip.domain.model.TodoCompletionInfo
import org.android.bbangzip.domain.model.TodoListInfo
import java.time.LocalDate
import java.time.LocalTime

interface TodoRepository {
    suspend fun getTodoList(date: String): Result<TodoListInfo>

    suspend fun toggleTodoCompletion(todoId: Long, isCompleted: Boolean): Result<TodoCompletionInfo>

    suspend fun reorderTodo(
        todoId: Long,
        originCategoryId: Long,
        targetCategoryId: Long,
        targetCategoryColor: String,
        todoOrderList: List<Long>,
    ): Result<Any>

    suspend fun addTodo(
        categoryId: Long,
        content: String,
        targetDate: LocalDate,
        startTime: LocalTime?,
    ): Result<NewTodo>
}