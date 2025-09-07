package org.android.bbangzip.data.repositoryImpl.remote

import org.android.bbangzip.data.datasource.remote.TimerRemoteDataSource
import org.android.bbangzip.data.dto.request.RequestCompleteTimerDto
import org.android.bbangzip.domain.model.BreadListEntity
import org.android.bbangzip.domain.model.CompleteBreadCountEntity
import org.android.bbangzip.domain.model.TodayBreadCountEntity
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
        ): Result<CompleteBreadCountEntity> =
            runCatching {
                val response =
                    timerRemoteDataSource.postTimerCompleted(
                        RequestCompleteTimerDto(targetDate = targetDate, count = count),
                    )

                val responseData = response.data ?: throw IllegalStateException(response.message)

                responseData.toCompleteTimerEntity()
            }

        override suspend fun fetchTodayBreadCount(): Result<TodayBreadCountEntity> =
            runCatching {
                val response = timerRemoteDataSource.getTodayBreadCount()

                val responseData = response.data ?: throw IllegalStateException(response.message)

                responseData.toTodayBreadEntity()
            }

        override suspend fun fetchBreadList(): Result<BreadListEntity> =
            runCatching {
                val response = timerRemoteDataSource.getBreadList()

                val responseData = response.data ?: throw IllegalStateException(response.message)

                responseData.toBreadListEntity()
            }
    }
