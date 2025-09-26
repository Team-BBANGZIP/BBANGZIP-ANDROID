package org.android.bbangzip.data.repository.remote

import org.android.bbangzip.data.source.remote.datasource.TimerRemoteDataSource
import org.android.bbangzip.data.source.remote.dto.request.RequestCompleteTimerDto
import org.android.bbangzip.domain.model.BreadList
import org.android.bbangzip.domain.repository.remote.TimerRepository
import javax.inject.Inject

class TimerRepositoryImpl
    @Inject
    constructor(
        private val timerRemoteDataSource: TimerRemoteDataSource,
    ) : TimerRepository {
        override suspend fun postTimerCompleted(
            targetDate: String,
            count: Int,
        ): Result<Int> =
            runCatching {
                val response =
                    timerRemoteDataSource.postTimerCompleted(
                        RequestCompleteTimerDto(targetDate = targetDate, count = count),
                    )

                val responseData = response.data ?: throw IllegalStateException(response.message)

                responseData.count
            }

        override suspend fun fetchTodayBreadCount(): Result<Int> =
            runCatching {
                val response = timerRemoteDataSource.getTodayBreadCount()

                val responseData = response.data ?: throw IllegalStateException(response.message)

                responseData.todayBakedCount
            }

        override suspend fun fetchBreadList(): Result<BreadList> =
            runCatching {
                val response = timerRemoteDataSource.getBreadList()

                val responseData = response.data ?: throw IllegalStateException(response.message)

                responseData.toBreadList()
            }
    }
