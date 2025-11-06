package org.android.bbangzip.data.repository.remote

import org.android.bbangzip.data.datasource.remote.TimerRemoteDataSource
import org.android.bbangzip.data.datasource.remote.dto.request.RequestCompleteTimerDto
import org.android.bbangzip.domain.model.BreadCount
import org.android.bbangzip.domain.model.BreadList
import org.android.bbangzip.domain.repository.TimerRepository
import javax.inject.Inject

class TimerRepositoryImpl
@Inject
constructor(
    private val timerRemoteDataSource: TimerRemoteDataSource,
) : TimerRepository {
    override suspend fun postTimerCompleted(
        targetDate: String,
        count: Int,
    ): Result<BreadCount> =
        runCatching {
            val response =
                timerRemoteDataSource.postTimerCompleted(
                    RequestCompleteTimerDto(targetDate = targetDate, count = count),
                )

            val responseData = response.data ?: throw IllegalStateException("Data가 존재하지 않습니다.")

            responseData.toBreadCount()
        }

    override suspend fun fetchTodayBreadCount(): Result<BreadCount> =
        runCatching {
            val response = timerRemoteDataSource.getTodayBreadCount()

            val responseData = response.data ?: throw IllegalStateException("Data가 존재하지 않습니다.")

            responseData.toBreadCount()
        }

    override suspend fun fetchBreadList(): Result<BreadList> =
        runCatching {
            val response = timerRemoteDataSource.getBreadList()

            val responseData = response.data ?: throw IllegalStateException("Data가 존재하지 않습니다.")

            responseData.toBreadList()
        }
}
