package org.android.bbangzip.data.repository.fake

import org.android.bbangzip.domain.model.Dummy
import org.android.bbangzip.domain.repository.DummyRepository
import javax.inject.Inject

class FakeDummyRepository
    @Inject
    constructor() : DummyRepository {
        override suspend fun fetchDummy(dummyId: Long): Result<Dummy> {
            return Result.success(Dummy(dummyName = "Dummy #$dummyId"))
        }
    }
