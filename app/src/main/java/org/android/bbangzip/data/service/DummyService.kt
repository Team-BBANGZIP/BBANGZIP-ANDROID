package org.android.bbangzip.data.service

import org.android.bbangzip.data.dto.response.ResponseDummyDto
import org.android.bbangzip.data.util.base.BaseResponse
import org.android.bbangzip.data.util.constant.ApiConstants.API
import org.android.bbangzip.data.util.constant.ApiConstants.ID
import retrofit2.http.GET
import retrofit2.http.Path

interface DummyService {
    @GET("$API/{$ID}")
    suspend fun getDummy(
        @Path(ID) dummyId: Long,
    ): BaseResponse<ResponseDummyDto>
}
