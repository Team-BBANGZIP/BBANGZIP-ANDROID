package org.android.bbangzip.data.datasource.remote.service

import org.android.bbangzip.data.datasource.remote.dto.response.ResponseDummyDto
import org.android.bbangzip.data.datasource.remote.util.base.BaseResponse
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.API
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.ID
import retrofit2.http.GET
import retrofit2.http.Path

interface DummyService {
    @GET("$API/{$ID}")
    suspend fun getDummy(
        @Path(ID) dummyId: Long,
    ): BaseResponse<ResponseDummyDto>
}
