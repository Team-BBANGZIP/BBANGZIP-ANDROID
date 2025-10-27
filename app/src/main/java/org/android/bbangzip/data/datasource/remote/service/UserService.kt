package org.android.bbangzip.data.datasource.remote.service

import org.android.bbangzip.data.datasource.remote.dto.request.RequestPostOnboardingDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPostUserInfoDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseGetReissueDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseGetUserDto
import org.android.bbangzip.data.datasource.remote.util.base.BaseResponse
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.API
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.AUTH
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.CODE
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.REISSUE
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.SIGN_IN
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.SIGN_OUT
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.SIGN_UP
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.VERSIONS
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.WITHDRAW
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("$API/$VERSIONS/$AUTH/$SIGN_IN")
    suspend fun login(
        @Query(CODE) code: String,
        @Body requestUserInfoDto: RequestPostUserInfoDto,
    ): BaseResponse<ResponseGetUserDto>

    @POST("$API/$VERSIONS/$AUTH/$REISSUE")
    suspend fun reissue(): BaseResponse<ResponseGetReissueDto>

    @DELETE("$API/$VERSIONS/$AUTH/$SIGN_OUT")
    suspend fun logout(): BaseResponse<String>

    @DELETE("$API/$VERSIONS/$AUTH/$WITHDRAW")
    suspend fun withdraw(): BaseResponse<String>

    @PATCH("$API/$VERSIONS/$AUTH/$SIGN_UP")
    suspend fun onboardingComplete(
        @Body requestOnboardingDto: RequestPostOnboardingDto,
    ): BaseResponse<String>
}