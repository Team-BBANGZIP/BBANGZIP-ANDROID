package org.android.bbangzip.domain.repository.remote

import org.android.bbangzip.domain.model.BreadListEntity
import org.android.bbangzip.domain.model.CompleteBreadCountEntity
import org.android.bbangzip.domain.model.TodayBreadCountEntity

interface TimerRepository {
    suspend fun postTimerCompleted(
        targetDate: String,
        count: Int,
    ): Result<CompleteBreadCountEntity>

    suspend fun fetchTodayBreadCount(): Result<TodayBreadCountEntity>

    suspend fun fetchBreadList(): Result<BreadListEntity>
}
