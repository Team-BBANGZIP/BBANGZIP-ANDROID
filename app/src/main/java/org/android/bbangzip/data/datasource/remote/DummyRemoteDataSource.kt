package org.android.bbangzip.data.datasource.remote

import org.android.bbangzip.data.datasource.remote.dto.response.ResponseDummyDto
import org.android.bbangzip.data.datasource.remote.service.DummyService
import org.android.bbangzip.data.datasource.remote.util.base.BaseResponse
import javax.inject.Inject

class DummyRemoteDataSource
    @Inject
    constructor(
        private val dummyService: DummyService,
    ) {
        suspend fun getDummy(dummyId: Long): BaseResponse<ResponseDummyDto> = dummyService.getDummy(dummyId = dummyId)
    }
