package org.android.bbangzip.data.datasource.remote

import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchTodoCompletionDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchTodoDateDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchTodoNameDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchTodoReOrderDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchTodoTimeDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPostTodoDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPostTodoRepeatDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseDeleteTodoDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseGetTodoListDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponsePatchTodoCompletionDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponsePatchTodoDateDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponsePatchTodoTimeDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponsePostTodoRepeatDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseTodoDto
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

    suspend fun patchTodoReOrder(
        todoId: Long,
        originCategoryId: Long,
        targetCategoryId: Long,
        targetCategoryColor: String,
        todoOrderList: List<Long>,
    ): BaseResponse<Unit> =
        todoService.patchTodoReOrder(
            requestTodoReOrderDto =
                RequestPatchTodoReOrderDto(
                    todoId = todoId,
                    originCategoryId = originCategoryId,
                    targetCategoryId = targetCategoryId,
                    targetCategoryColor = targetCategoryColor,
                    todoList = todoOrderList,
                ),
        )

    suspend fun patchTodoCompletion(
        todoId: Long,
        isCompleted: Boolean,
    ): BaseResponse<ResponsePatchTodoCompletionDto> =
        todoService.patchTodoCompletion(
            todoId = todoId,
            requestTodoCompletionDto =
                RequestPatchTodoCompletionDto(
                    isCompleted = isCompleted,
                ),
        )

    suspend fun postTodo(
        categoryId: Long,
        content: String,
        targetDate: LocalDate,
        startTime: LocalTime?,
    ): BaseResponse<ResponseTodoDto> =
        todoService.postTodo(
            requestTodoAddDto =
                RequestPostTodoDto(
                    categoryId = categoryId,
                    content = content,
                    targetDate = targetDate,
                    startTime = startTime,
                ),
        )

    suspend fun patchTodoName(
        todoId: Long,
        content: String,
    ): BaseResponse<Unit> =
        todoService.patchTodoName(
            todoId = todoId,
            requestPatchTodoNameDto =
                RequestPatchTodoNameDto(
                    content = content,
                ),
        )

    suspend fun deleteTodo(
        todoId: Long,
    ): BaseResponse<ResponseDeleteTodoDto> =
        todoService.deleteTodo(
            todoId = todoId,
        )

    suspend fun postTodoCopy(
        todoId: Long,
    ): BaseResponse<ResponseTodoDto> =
        todoService.postTodoCopy(
            todoId = todoId,
        )

    suspend fun patchTodoDate(
        todoId: Long,
        targetDate: LocalDate?,
    ): BaseResponse<ResponsePatchTodoDateDto> =
        todoService.patchTodoDate(
            todoId = todoId,
            requestPatchTodoDateDto =
                RequestPatchTodoDateDto(
                    targetDate = targetDate,
                ),
        )

    suspend fun patchTodoTime(
        todoId: Long,
        startTime: LocalTime?,
    ): BaseResponse<ResponsePatchTodoTimeDto> =
        todoService.patchTodoTime(
            todoId = todoId,
            requestPatchTodoTimeDto =
                RequestPatchTodoTimeDto(
                    startTime = startTime,
                ),
        )

    suspend fun postTodoRepeat(
        todoId: Long,
        targetDate: LocalDate,
    ): BaseResponse<ResponsePostTodoRepeatDto> =
        todoService.postTodoRepeat(
            todoId = todoId,
            requestPostTodoRepeatDto =
                RequestPostTodoRepeatDto(
                    targetDate = targetDate,
                ),
        )
}
