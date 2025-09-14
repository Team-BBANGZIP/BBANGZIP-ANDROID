package org.android.bbangzip.domain.usecase

import org.android.bbangzip.domain.model.BreadListEntity
import org.android.bbangzip.domain.repository.remote.TimerRepository
import javax.inject.Inject

class SelectBreadUseCase
    @Inject
    constructor(
        private val timerRepository: TimerRepository,
    ) {
        suspend operator fun invoke(
            targetDate: String,
            count: Int,
        ): Result<BreadListEntity> = timerRepository.fetchBreadList()
    }
