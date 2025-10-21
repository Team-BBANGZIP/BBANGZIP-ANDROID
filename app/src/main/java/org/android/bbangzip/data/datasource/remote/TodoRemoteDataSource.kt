package org.android.bbangzip.data.datasource.remote

import org.android.bbangzip.data.datasource.remote.dto.request.RequestTodoCompletionDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestTodoOrderDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseTodoCompletionDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseTodoListDto
import org.android.bbangzip.data.datasource.remote.service.TodoService
import org.android.bbangzip.data.datasource.remote.util.base.BaseResponse
import javax.inject.Inject

class TodoRemoteDataSource
    @Inject
    constructor(
        private val todoService: TodoService,
    ) {
        suspend fun getTodoList(date: String): BaseResponse<ResponseTodoListDto> = todoService.getTodoList(date)

        suspend fun patchTodoOrder(request: RequestTodoOrderDto): BaseResponse<Any?> = todoService.patchTodoOrder(request)

        suspend fun patchTodoCompletion(
            todoId: Long,
            isCompleted: Boolean,
        ): BaseResponse<ResponseTodoCompletionDto> = todoService.patchTodoCompletion(
            todoId = todoId,
            requestTodoCompletionDto = RequestTodoCompletionDto(
                isCompleted = isCompleted,
            ),
        )
}