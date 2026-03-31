package org.android.bbangzip.data.datasource.remote

import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchProfileInformationDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPostOnboardingDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPostUserInfoDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseGetReissueDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseGetUserDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseProfileInformationDto
import org.android.bbangzip.data.datasource.remote.service.UserService
import org.android.bbangzip.data.datasource.remote.util.base.BaseResponse
import javax.inject.Inject

class UserRemoteDataSource
    @Inject
    constructor(
        private val userService: UserService,
    ) {
        suspend fun login(
            code: String,
            requestUserInfoDto: RequestPostUserInfoDto,
        ): BaseResponse<ResponseGetUserDto> =
            userService.login(providerToken = code, requestUserInfoDto = requestUserInfoDto)

        suspend fun reissue(): BaseResponse<ResponseGetReissueDto> = userService.reissue()

        suspend fun logout(): BaseResponse<Unit> = userService.logout()

        suspend fun withDraw(): BaseResponse<Unit> = userService.withdraw()

        suspend fun onboardingComplete(requestOnboardingDto: RequestPostOnboardingDto): BaseResponse<Unit> = userService.onboardingComplete(requestOnboardingDto = requestOnboardingDto)

        suspend fun getProfileInformation() : BaseResponse<ResponseProfileInformationDto> = userService.getProfileInformation()

        suspend fun patchProfileInformation(
            profileImageKey: Int,
            nickname: String,
            commitmentMessage: String,
        ) : BaseResponse<ResponseProfileInformationDto> {
            val requestProfileInformationDto = RequestPatchProfileInformationDto(
                profileImageKey = profileImageKey,
                nickname = nickname,
                commitmentMessage = commitmentMessage
            )

            return userService.patchProfileInformation(requestProfileInformationDto = requestProfileInformationDto)
        }
    }
