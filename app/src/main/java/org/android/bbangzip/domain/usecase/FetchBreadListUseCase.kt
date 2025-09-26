package org.android.bbangzip.domain.usecase

import org.android.bbangzip.domain.model.BreadList
import org.android.bbangzip.domain.repository.remote.TimerRepository
import javax.inject.Inject

class FetchBreadListUseCase
    @Inject
    constructor(
        private val timerRepository: TimerRepository,
    ) {
        suspend operator fun invoke(
            targetDate: String,
            count: Int,
        ): Result<BreadList> = timerRepository.fetchBreadList()
    }
