package org.android.bbangzip.data.datasource.remote.service

import org.android.bbangzip.data.datasource.remote.dto.response.ResponseTodoListDto
import org.android.bbangzip.data.datasource.remote.util.base.BaseResponse
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.API
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.VERSIONS
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.TODO
import retrofit2.http.GET
import retrofit2.http.Query

interface TodoService {
    @GET("$API/$VERSIONS/$TODO")
    suspend fun getTodoList(
        @Query("date") date: String
    ): BaseResponse<ResponseTodoListDto?>
}