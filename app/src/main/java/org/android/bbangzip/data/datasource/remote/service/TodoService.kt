package org.android.bbangzip.data.datasource.remote.service

import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchTodoCompletionDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchTodoDateDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchTodoNameDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchTodoOrderDto
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
import org.android.bbangzip.data.datasource.remote.util.base.BaseResponse
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.API
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.TODO
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.VERSIONS
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoService {
    @GET("$API/$VERSIONS/$TODO")
    suspend fun getTodoList(
        @Query("date") date: String,
    ): BaseResponse<ResponseGetTodoListDto>

    @PATCH("$API/$VERSIONS/$TODO/{todoId}/completion")
    suspend fun patchTodoCompletion(
        @Path("todoId") todoId: Long,
        @Body requestTodoCompletionDto: RequestPatchTodoCompletionDto,
    ): BaseResponse<ResponsePatchTodoCompletionDto>

    @PATCH("$API/$VERSIONS/$TODO/order")
    suspend fun patchTodoOrder(
        @Body requestTodoOrderDto: RequestPatchTodoOrderDto,
    ): BaseResponse<Any>

    @POST("$API/$VERSIONS/$TODO")
    suspend fun postTodo(
        @Body requestTodoAddDto: RequestPostTodoDto,
    ): BaseResponse<ResponseTodoDto>

    @PATCH("$API/$VERSIONS/$TODO/{todoId}")
    suspend fun patchTodoName(
        @Path("todoId") todoId: Long,
        @Body requestPatchTodoNameDto: RequestPatchTodoNameDto,
    ): BaseResponse<Any>

    @DELETE("$API/$VERSIONS/$TODO/{todoId}")
    suspend fun deleteTodo(
        @Path("todoId") todoId: Long,
    ): BaseResponse<ResponseDeleteTodoDto>

    @POST("$API/$VERSIONS/$TODO/{todoId}/copy")
    suspend fun postTodoCopy(
        @Path("todoId") todoId: Long,
    ): BaseResponse<ResponseTodoDto>

    @PATCH("$API/$VERSIONS/$TODO/{todoId}/reschedule")
    suspend fun patchTodoDate(
        @Path("todoId") todoId: Long,
        @Body requestPatchTodoDateDto: RequestPatchTodoDateDto,
    ): BaseResponse<ResponsePatchTodoDateDto>

    @PATCH("$API/$VERSIONS/$TODO/{todoId}/start-time")
    suspend fun patchTodoTime(
        @Path("todoId") todoId: Long,
        @Body requestPatchTodoTimeDto: RequestPatchTodoTimeDto
    ): BaseResponse<ResponsePatchTodoTimeDto>

    @POST("$API/$VERSIONS/$TODO/{todoId}/repeat")
    suspend fun postTodoRepeat(
        @Path("todoId") todoId: Long,
        @Body requestPostTodoRepeatDto: RequestPostTodoRepeatDto,
    ): BaseResponse<ResponsePostTodoRepeatDto>
}
