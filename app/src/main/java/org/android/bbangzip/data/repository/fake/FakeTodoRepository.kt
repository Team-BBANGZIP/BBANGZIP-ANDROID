package org.android.bbangzip.data.repository.fake

import org.android.bbangzip.domain.model.Category
import org.android.bbangzip.domain.model.Todo
import org.android.bbangzip.domain.model.TodoCompletionInfo
import org.android.bbangzip.domain.model.TodoCount
import org.android.bbangzip.domain.model.TodoList
import org.android.bbangzip.domain.model.TodoSummary
import org.android.bbangzip.domain.repository.TodoRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import kotlin.random.Random

class FakeTodoRepository
    @Inject
    constructor() : TodoRepository {
        override suspend fun getTodoList(date: String): Result<TodoList> {
            return Result.success(
                TodoList(
                    commitmentMessage = "Fake Commitment",
                    todoSummary = TodoSummary(date = LocalDate.now(), totalCount = 0, completedCount = 0),
                    categories =
                        listOf(
                            Category(categoryId = 1, categoryName = "Fake Category", categoryColor = "RED1", isStopped = false, todos = emptyList()),
                        ),
                ),
            )
        }

        override suspend fun toggleTodoCompletion(
            todoId: Long,
            isCompleted: Boolean,
        ): Result<TodoCompletionInfo> {
            return Result.success(TodoCompletionInfo(todoId = todoId, isCompleted = isCompleted, completionCount = 1, totalTodoCount = 2))
        }

        override suspend fun reorderTodo(
            todoId: Long,
            originCategoryId: Long,
            targetCategoryId: Long,
            targetCategoryColor: String,
            todoOrderList: List<Long>,
        ): Result<Any> {
            return Result.success(Any())
        }

        override suspend fun addTodo(
            categoryId: Long,
            content: String,
            targetDate: LocalDate,
            startTime: LocalTime?,
        ): Result<Todo> {
            return Result.success(
                Todo(
                    todoId = Random.nextInt(),
                    content = content,
                    isCompleted = false,
                    startTime = startTime,
                ),
            )
        }

        override suspend fun modifyTodoName(
            todoId: Long,
            content: String,
        ): Result<Any> {
            return Result.success(Any())
        }

        override suspend fun deleteTodo(todoId: Long): Result<TodoCount> {
            return Result.success(TodoCount(completedCount = 0, totalCount = 0))
        }

        override suspend fun copyTodo(todoId: Long): Result<Todo> {
            return Result.success(
                Todo(
                    todoId = 1000,
                    content = "Copied Todo",
                    isCompleted = false,
                    startTime = null,
                ),
            )
        }

        override suspend fun modifyTodoDate(
            todoId: Long,
            targetDate: LocalDate?,
        ): Result<Todo> {
            return Result.success(
                Todo(
                    todoId = todoId.toInt(),
                    content = "Modified Date",
                    isCompleted = false,
                    startTime = null,
                ),
            )
        }

        override suspend fun modifyTodoTime(
            todoId: Long,
            startTime: LocalTime?,
        ): Result<Todo> {
            return Result.success(
                Todo(
                    todoId = todoId.toInt(),
                    content = "Modified Time",
                    isCompleted = false,
                    startTime = startTime,
                ),
            )
        }

        override suspend fun repeatTodo(
            todoId: Long,
            targetDate: LocalDate,
        ): Result<Todo> {
            return Result.success(
                Todo(
                    todoId = 1001,
                    content = "Repeated Todo",
                    isCompleted = false,
                    startTime = null,
                ),
            )
        }
    }
