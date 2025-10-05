package org.android.bbangzip.domain.usecase

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.android.bbangzip.domain.model.BreadCount
import org.android.bbangzip.domain.model.BreadList
import org.android.bbangzip.domain.repository.TimerRepository
import javax.inject.Inject

class InitializeTimerScreenUseCase
@Inject
constructor(
    private val timerRepository: TimerRepository,
) {
    suspend operator fun invoke(): Result<TimerInitialData> =
        coroutineScope {
            try {
                val breadCountDeferred = async { timerRepository.fetchTodayBreadCount().getOrThrow() }
                val breadListDeferred = async { timerRepository.fetchBreadList().getOrThrow() }

                val breadCount = breadCountDeferred.await()
                val breadList = breadListDeferred.await()

                Result.success(
                    TimerInitialData(
                        todayBreadCount = breadCount,
                        breadList = breadList,
                    ),
                )
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}

data class TimerInitialData(
    val todayBreadCount: BreadCount,
    val breadList: BreadList,
)