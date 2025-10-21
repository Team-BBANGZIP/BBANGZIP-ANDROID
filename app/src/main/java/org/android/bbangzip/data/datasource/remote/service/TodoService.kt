package org.android.bbangzip.data.datasource.remote.service

import org.android.bbangzip.data.datasource.remote.dto.request.RequestTodoCompletionDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestTodoOrderDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseDummyDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseTodoCompletionDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseTodoListDto
import org.android.bbangzip.data.datasource.remote.util.base.BaseResponse
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.API
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.VERSIONS
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.TODO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoService {
    @GET("$API/$VERSIONS/$TODO")
    suspend fun getTodoList(
        @Query("date") date: String
    ): BaseResponse<ResponseTodoListDto>

    @PATCH("$API/$VERSIONS/$TODO/{todoId}/completion")
    suspend fun patchTodoCompletion(
        @Path("todoId") todoId: Long,
        @Body requestTodoCompletionDto: RequestTodoCompletionDto,
    ): BaseResponse<ResponseTodoCompletionDto>

    @PATCH("$API/$VERSIONS/$TODO/order")
    suspend fun patchTodoOrder(
        @Body requestTodoOrderDto: RequestTodoOrderDto,
    ): BaseResponse<ResponseDummyDto>
}