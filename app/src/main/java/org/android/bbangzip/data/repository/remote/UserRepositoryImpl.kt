package org.android.bbangzip.data.repository.remote

import org.android.bbangzip.data.datasource.remote.UserRemoteDataSource
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPostUserInfoDto
import org.android.bbangzip.domain.model.OnboardingInfo
import org.android.bbangzip.domain.model.ReissueToken
import org.android.bbangzip.domain.model.UserTokenInfo
import org.android.bbangzip.domain.repository.UserRepository
import org.android.bbangzip.presentation.common.util.device.AppInfo
import org.android.bbangzip.presentation.common.util.device.DeviceInfo
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val userRemoteDataSource: UserRemoteDataSource,
        private val deviceInfo: DeviceInfo,
        private val appInfo: AppInfo,
    ) : UserRepository {
        override suspend fun login(code: String): Result<UserTokenInfo> =
            runCatching {
                Timber.d("[카카오 로그인] -> 액세스 토큰 $code")

                val request =
                    RequestPostUserInfoDto(
                        deviceName = deviceInfo.deviceName,
                        deviceType = deviceInfo.deviceType,
                        provider = "KAKAO",
                        role = "USER",
                        appVersion = appInfo.appVersion,
                        osVersion = deviceInfo.osVersion,
                        osType = deviceInfo.osType,
                    )

                val response = userRemoteDataSource.login(code = code, request)
                val responseData = response.data
                responseData!!.toUserTokenInfo()
            }

        override suspend fun logout(): Result<Unit> =
            runCatching {
                userRemoteDataSource.logout()
            }

        override suspend fun reissue(): Result<ReissueToken> =
            runCatching {
                val response = userRemoteDataSource.reissue()
                val responseData = response.data
                responseData!!.toReissueToken()
            }

        override suspend fun withdraw(): Result<Unit> =
            runCatching {
                userRemoteDataSource.withDraw()
            }

        override suspend fun onboardingComplete(onboardingEntity: OnboardingInfo): Result<Unit> =
            runCatching {
                userRemoteDataSource.onboardingComplete(requestOnboardingDto = onboardingEntity.toRequestPostOnboardingDto())
            }
    }
