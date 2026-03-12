package org.android.bbangzip.data.repository.fake

import org.android.bbangzip.domain.model.BreadCount
import org.android.bbangzip.domain.model.BreadList
import org.android.bbangzip.domain.repository.TimerRepository
import javax.inject.Inject

class FakeTimerRepository @Inject constructor() : TimerRepository {
    override suspend fun postTimerCompleted(targetDate: String, count: Int): Result<BreadCount> {
        return Result.success(BreadCount(count = count))
    }

    override suspend fun fetchTodayBreadCount(): Result<BreadCount> {
        return Result.success(BreadCount(count = 2))
    }

    override suspend fun fetchBreadList(): Result<BreadList> {
        return Result.success(BreadList(totalCount = 5, breadList = emptyList()))
    }
}
