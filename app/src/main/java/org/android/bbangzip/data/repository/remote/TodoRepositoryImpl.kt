package org.android.bbangzip.data.repository.remote

import org.android.bbangzip.data.datasource.remote.TodoRemoteDataSource
import org.android.bbangzip.domain.model.Todo
import org.android.bbangzip.domain.model.TodoCompletionInfo
import org.android.bbangzip.domain.model.TodoCount
import org.android.bbangzip.domain.model.TodoList
import org.android.bbangzip.domain.repository.TodoRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class TodoRepositoryImpl
    @Inject
    constructor(
        private val todoRemoteDataSource: TodoRemoteDataSource,
    ) : TodoRepository {
        override suspend fun getTodoList(date: String): Result<TodoList> =
            runCatching {
                val response =
                    todoRemoteDataSource.getTodoList(date)
                val responseData = response.data ?: throw IllegalStateException("Data가 존재하지 않습니다.")

                responseData.toTodoList()
            }

        override suspend fun toggleTodoCompletion(
            todoId: Long,
            isCompleted: Boolean,
        ): Result<TodoCompletionInfo> =
            runCatching {
                val response =
                    todoRemoteDataSource.patchTodoCompletion(todoId, isCompleted)

                val responseData = response.data ?: throw IllegalStateException("Data가 존재하지 않습니다.")

                responseData.toTodoCompletionInfo()
            }

        override suspend fun reorderTodo(
            todoId: Long,
            originCategoryId: Long,
            targetCategoryId: Long,
            targetCategoryColor: String,
            todoOrderList: List<Long>,
        ): Result<Any> =
            runCatching {
                todoRemoteDataSource.patchTodoOrder(
                    todoId = todoId,
                    originCategoryId = originCategoryId,
                    targetCategoryId = targetCategoryId,
                    targetCategoryColor = targetCategoryColor,
                    todoOrderList = todoOrderList,
                )
            }

        override suspend fun addTodo(
            categoryId: Long,
            content: String,
            targetDate: LocalDate,
            startTime: LocalTime?,
        ): Result<Todo> =
            runCatching {
                val response =
                    todoRemoteDataSource.postTodo(
                        categoryId = categoryId,
                        content = content,
                        targetDate = targetDate,
                        startTime = startTime,
                    )

                val data = response.data ?: throw IllegalStateException("Todo Data가 존재하지 않습니다.")

                data.toTodo()
            }

    override suspend fun modifyTodoName(todoId: Long, content: String): Result<Any> =
        runCatching{
            todoRemoteDataSource.patchTodoName(
                todoId = todoId,
                content = content,
            )
        }

    override suspend fun deleteTodo(todoId: Long): Result<TodoCount> =
        runCatching{
            val response = todoRemoteDataSource.deleteTodo(todoId)
            val data = response.data ?: throw IllegalStateException("삭제 Data가 존재하지 않습니다.")
            data.toTodoCount()
        }

    override suspend fun copyTodo(todoId: Long): Result<Todo> =
        runCatching {
            val response = todoRemoteDataSource.postTodoCopy(todoId)
            val data = response.data ?: throw IllegalStateException("복제 Data가 존재하지 않습니다.")
            data.toTodo()
        }

    override suspend fun modifyTodoDate(todoId: Long, targetDate: LocalDate): Result<Todo> =
        runCatching {
            val response = todoRemoteDataSource.patchTodoDate(
                todoId = todoId,
                targetDate = targetDate
            )
            val data = response.data ?: throw IllegalStateException("Todo Data가 존재하지 않습니다.")
            data.toTodo()
        }

    override suspend fun modifyTodoTime(todoId: Long, startTime: LocalTime?): Result<Todo> =
        runCatching {
            val response = todoRemoteDataSource.patchTodoTime(
                todoId = todoId,
                startTime = startTime
            )

            val data = response.data ?: throw IllegalStateException("Todo Data가 존재하지 않습니다.")
            data.toTodo()
        }

    override suspend fun repeatTodo(todoId: Long, targetDate: LocalDate): Result<Todo> =
        runCatching {
            val response = todoRemoteDataSource.postTodoRepeat(
                todoId = todoId,
                targetDate = targetDate
            )
            val data = response.data ?: throw IllegalStateException("Todo Data가 존재하지 않습니다.")
            data.toTodo()
        }
    }
