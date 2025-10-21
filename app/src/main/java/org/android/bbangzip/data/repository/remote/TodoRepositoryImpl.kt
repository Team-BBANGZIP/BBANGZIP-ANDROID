package org.android.bbangzip.data.repository.remote

import org.android.bbangzip.data.datasource.remote.TodoRemoteDataSource
import org.android.bbangzip.domain.model.TodoCompletionInfo
import org.android.bbangzip.domain.model.TodoListInfo
import org.android.bbangzip.domain.repository.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl
    @Inject
    constructor(
        private val todoRemoteDataSource: TodoRemoteDataSource
    ) : TodoRepository {
    override suspend fun getTodoList(date: String): Result<TodoListInfo> =
        runCatching {
            val response =
                todoRemoteDataSource.getTodoList(date)
            val responseData = response.data ?: throw IllegalStateException("Data가 존재하지 않습니다.")

            responseData.toTodoListInfo()
        }

    override suspend fun patchTodoCompletion(todoId: Long, isCompleted: Boolean): Result<TodoCompletionInfo> =
        runCatching {
            val response =
                todoRemoteDataSource.patchTodoCompletion(todoId, isCompleted)

            val responseData = response.data ?: throw IllegalStateException("Data가 존재하지 않습니다.")

            responseData.toTodoCompletionInfo()
        }

    override suspend fun patchTodoOrder(
        todoId: Long,
        originCategoryId: Long,
        targetCategoryId: Long,
        targetCategoryColor: String,
        todoOrderList: List<Long>
    ): Result<Any> = runCatching {
        todoRemoteDataSource.patchTodoOrder(
            todoId,
            originCategoryId,
            targetCategoryId,
            targetCategoryColor,
            todoOrderList
        )
    }
}