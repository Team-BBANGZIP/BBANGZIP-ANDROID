package org.android.bbangzip.data.repository.remote

import org.android.bbangzip.data.source.remote.datasource.DummyRemoteDataSource
import org.android.bbangzip.domain.model.Dummy
import org.android.bbangzip.domain.repository.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl
    @Inject
    constructor(
        private val dummyRemoteDataSource: DummyRemoteDataSource,
    ) : DummyRepository {
        override suspend fun fetchDummy(dummyId: Long): Result<Dummy> =
            runCatching {
                dummyRemoteDataSource.getDummy(dummyId = dummyId).data.toDummyEntity()
            }
    }
