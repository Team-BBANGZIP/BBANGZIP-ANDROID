package org.android.bbangzip.domain.repository

import org.android.bbangzip.domain.model.BreadList

interface TimerRepository {
    suspend fun postTimerCompleted(
        targetDate: String,
        count: Int,
    ): Result<Int>

    suspend fun fetchTodayBreadCount(): Result<Int>

    suspend fun fetchBreadList(): Result<BreadList>
}
