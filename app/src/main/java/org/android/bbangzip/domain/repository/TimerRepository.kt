package org.android.bbangzip.domain.repository

import org.android.bbangzip.domain.model.BreadCount
import org.android.bbangzip.domain.model.BreadList

interface TimerRepository {
    suspend fun postTimerCompleted(
        targetDate: String,
        count: Int,
    ): Result<BreadCount>

    suspend fun fetchTodayBreadCount(): Result<BreadCount>

    suspend fun fetchBreadList(): Result<BreadList>
}
