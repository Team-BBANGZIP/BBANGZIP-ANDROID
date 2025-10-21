package org.android.bbangzip.data.datasource.remote.service

import org.android.bbangzip.data.datasource.remote.dto.request.RequestPostCommitmentDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponsePostCommitmentDto
import org.android.bbangzip.data.datasource.remote.util.base.BaseResponse
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.API
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.USER
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.VERSIONS
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("$API/$VERSIONS/$USER/commitments")
    suspend fun postTodoCommitment(
        @Body requestTodoCommitmentDto: RequestPostCommitmentDto,
    ): BaseResponse<ResponsePostCommitmentDto>
}
