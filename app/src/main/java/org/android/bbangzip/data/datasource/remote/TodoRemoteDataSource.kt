package org.android.bbangzip.data.datasource.remote

import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchTodoCompletionDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchTodoOrderDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPostTodoDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseGetTodoListDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponsePatchTodoCompletionDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponsePostTodoDto
import org.android.bbangzip.data.datasource.remote.service.TodoService
import org.android.bbangzip.data.datasource.remote.util.base.BaseResponse
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class TodoRemoteDataSource
    @Inject
    constructor(
        private val todoService: TodoService,
    ) {
        suspend fun getTodoList(date: String): BaseResponse<ResponseGetTodoListDto> = todoService.getTodoList(date)

        suspend fun patchTodoOrder(
            todoId: Long,
            originCategoryId: Long,
            targetCategoryId: Long,
            targetCategoryColor: String,
            todoOrderList: List<Long>,
        ): BaseResponse<Any> = todoService.patchTodoOrder(
            requestTodoOrderDto = RequestPatchTodoOrderDto(
                todoId = todoId,
                originCategoryId = originCategoryId,
                targetCategoryId = targetCategoryId,
                targetCategoryColor = targetCategoryColor,
                todoList = todoOrderList,
            )
        )

        suspend fun patchTodoCompletion(
            todoId: Long,
            isCompleted: Boolean,
        ): BaseResponse<ResponsePatchTodoCompletionDto> = todoService.patchTodoCompletion(
            todoId = todoId,
            requestTodoCompletionDto = RequestPatchTodoCompletionDto(
                isCompleted = isCompleted,
            ),
        )

    suspend fun postTodo(
        categoryId: Long,
        content: String,
        targetDate: LocalDate,
        startTime: LocalTime?,
    ): BaseResponse<ResponsePostTodoDto> = todoService.postTodo(
        requestTodoAddDto = RequestPostTodoDto(
            categoryId = categoryId,
            content = content,
            targetDate = targetDate,
            startTime = startTime
        )
    )
}