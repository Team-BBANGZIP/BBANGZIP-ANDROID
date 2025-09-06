package org.android.bbangzip.data.datasource.remote

import org.android.bbangzip.data.dto.request.RequestCompleteTimerDto
import org.android.bbangzip.data.dto.response.ResponseBreadListDto
import org.android.bbangzip.data.dto.response.ResponseCompleteTimerDto
import org.android.bbangzip.data.dto.response.ResponseTodayBreadDto
import org.android.bbangzip.data.service.TimerService
import org.android.bbangzip.data.util.base.BaseResponse
import javax.inject.Inject

class TimerRemoteDataSource
@Inject
constructor(
    private val timerService: TimerService,
) {

    // 오늘 구운 빵 개수 조회
    suspend fun getTodayBreadCount(): BaseResponse<ResponseTodayBreadDto?> = timerService.getTodayBreadCount()

    // 빵 리스트 조회
    suspend fun getBreadList(): BaseResponse<ResponseBreadListDto?> = timerService.getBreadList()

    // 타이머 완료 처리
    suspend fun postTimerCompleted(
        requestCompleteTimerDto: RequestCompleteTimerDto,
    ): BaseResponse<ResponseCompleteTimerDto?> = timerService.postTimerCompleted(requestCompleteTimerDto)
}