package org.android.bbangzip.data.datasource.remote

import org.android.bbangzip.data.datasource.remote.dto.response.ResponseTodoListDto
import org.android.bbangzip.data.datasource.remote.service.TodoService
import org.android.bbangzip.data.datasource.remote.util.base.BaseResponse
import javax.inject.Inject

class TodoRemoteDataSource
    @Inject
    constructor(
        private val todoService: TodoService,
    ) {
        suspend fun getTodoList(date: String): BaseResponse<ResponseTodoListDto?> = todoService.getTodoList(date)
}