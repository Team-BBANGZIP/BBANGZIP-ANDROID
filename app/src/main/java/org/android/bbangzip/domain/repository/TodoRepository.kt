package org.android.bbangzip.domain.repository

import org.android.bbangzip.domain.model.Todo
import org.android.bbangzip.domain.model.TodoCompletionInfo
import org.android.bbangzip.domain.model.TodoCount
import org.android.bbangzip.domain.model.TodoList
import java.time.LocalDate
import java.time.LocalTime

interface TodoRepository {
    suspend fun getTodoList(date: String): Result<TodoList>

    suspend fun toggleTodoCompletion(
        todoId: Long,
        isCompleted: Boolean,
    ): Result<TodoCompletionInfo>

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
    ): Result<Todo>

    suspend fun modifyTodoName(
        todoId: Long,
        content: String,
    ): Result<Any>

    suspend fun deleteTodo(
        todoId: Long,
    ): Result<TodoCount>

    suspend fun copyTodo(
        todoId: Long,
    ): Result<Todo>

    suspend fun modifyTodoDate(
        todoId: Long,
        targetDate: LocalDate,
    ): Result<Todo>

    suspend fun modifyTodoTime(
        todoId: Long,
        startTime: LocalTime?,
    ): Result<Todo>

    suspend fun repeatTodo(
        todoId: Long,
        targetDate: LocalDate,
    ): Result<Todo>
}
