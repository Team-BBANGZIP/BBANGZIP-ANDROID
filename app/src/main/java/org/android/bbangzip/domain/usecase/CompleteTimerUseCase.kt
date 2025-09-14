package org.android.bbangzip.domain.usecase

import org.android.bbangzip.domain.model.CompleteBreadCountEntity
import org.android.bbangzip.domain.repository.remote.TimerRepository
import javax.inject.Inject

class CompleteTimerUseCase
    @Inject
    constructor(
        private val timerRepository: TimerRepository,
    ) {
        suspend operator fun invoke(
            targetDate: String,
            count: Int,
        ): Result<CompleteBreadCountEntity> = timerRepository.postTimerCompleted(targetDate = targetDate, count = count)
    }
