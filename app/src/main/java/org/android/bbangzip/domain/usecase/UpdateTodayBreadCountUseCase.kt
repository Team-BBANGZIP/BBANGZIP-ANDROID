package org.android.bbangzip.domain.usecase

import org.android.bbangzip.domain.repository.remote.TimerRepository
import javax.inject.Inject

class UpdateTodayBreadCountUseCase
    @Inject
    constructor(
        private val timerRepository: TimerRepository,
    ) {
        suspend operator fun invoke(): Result<Int> = timerRepository.fetchTodayBreadCount()
    }
