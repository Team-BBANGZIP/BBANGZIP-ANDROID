package org.android.bbangzip.data.service

import org.android.bbangzip.data.dto.request.RequestCompleteTimerDto
import org.android.bbangzip.data.dto.response.ResponseBreadListDto
import org.android.bbangzip.data.dto.response.ResponseCompleteTimerDto
import org.android.bbangzip.data.dto.response.ResponseTodayBreadDto
import org.android.bbangzip.data.util.base.BaseResponse
import org.android.bbangzip.data.util.constant.ApiConstants.API
import org.android.bbangzip.data.util.constant.ApiConstants.TIMER
import org.android.bbangzip.data.util.constant.ApiConstants.VERSIONS
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TimerService {
    @GET("$API/$VERSIONS/$TIMER/today-count")
    suspend fun getTodayBreadCount(): BaseResponse<ResponseTodayBreadDto?>

    @GET("$API/$VERSIONS/$TIMER/breads")
    suspend fun getBreadList(): BaseResponse<ResponseBreadListDto?>

    @POST("$API/$VERSIONS/$TIMER")
    suspend fun postTimerCompleted(
        @Body requestCompleteTimerDto: RequestCompleteTimerDto,
    ): BaseResponse<ResponseCompleteTimerDto?>
}