package org.android.bbangzip.domain.repository.remote

import org.android.bbangzip.domain.model.Dummy

interface DummyRepository {
    suspend fun fetchDummy(dummyId: Long): Result<Dummy>
}
