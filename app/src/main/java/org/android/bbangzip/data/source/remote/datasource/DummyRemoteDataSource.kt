package org.android.bbangzip.data.source.remote.datasource

import org.android.bbangzip.data.source.remote.dto.response.ResponseDummyDto
import org.android.bbangzip.data.source.remote.service.DummyService
import org.android.bbangzip.data.source.remote.util.base.BaseResponse
import javax.inject.Inject

class DummyRemoteDataSource
    @Inject
    constructor(
        private val dummyService: DummyService,
    ) {
        suspend fun getDummy(dummyId: Long): BaseResponse<ResponseDummyDto> = dummyService.getDummy(dummyId = dummyId)
    }
