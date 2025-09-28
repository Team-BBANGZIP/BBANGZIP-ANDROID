package org.android.bbangzip.domain.usecase

import org.android.bbangzip.domain.model.Dummy
import org.android.bbangzip.domain.repository.remote.DummyRepository
import javax.inject.Inject

class FetchDummyUseCase
    @Inject
    constructor(
        private val dummyRepository: DummyRepository,
    ) {
        suspend operator fun invoke(id: Long): Result<Dummy?> = dummyRepository.fetchDummy(dummyId = id)
    }
