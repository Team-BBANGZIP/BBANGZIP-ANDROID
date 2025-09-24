package org.android.bbangzip.data.repositoryImpl.remote

import org.android.bbangzip.data.datasource.remote.UserRemoteDataSource
import org.android.bbangzip.data.dto.request.RequestUserInfoDto
import org.android.bbangzip.domain.model.OnboardingEntity
import org.android.bbangzip.domain.model.ReissueEntity
import org.android.bbangzip.domain.model.UserEntity
import org.android.bbangzip.domain.repository.remote.UserRepository
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    override suspend fun login(code: String): Result<UserEntity> =
        runCatching {
            Timber.d("[카카오 로그인] -> 액세스 토큰 $code")
            // TODO device info 담는 것으로 수정
            val request = RequestUserInfoDto(
                deviceName = " ",
                deviceType = " ",
                provider = "KAKAO",
                role = "a",
                appVersion = "1.0",
                osVersion = " ",
                osType = " "
            )

            val response = userRemoteDataSource.login(code = code, request)
            val responseData = response.data
            responseData.toUserEntity()
        }

    override suspend fun logout(): Result<String> =
        runCatching {
            val response = userRemoteDataSource.logout()
            val responseData = response.data
            responseData
        }

    override suspend fun reissue(): Result<ReissueEntity> =
        runCatching {
            val response = userRemoteDataSource.reissue()
            val responseData = response.data
            responseData.toReissueEntity()
        }

    override suspend fun withdraw(): Result<String> =
        runCatching {
            val response = userRemoteDataSource.withDraw()
            val responseData = response.data
            responseData
        }

    override suspend fun onboardingComplete(onboardingEntity: OnboardingEntity): Result<String> =
        runCatching {
            val response = userRemoteDataSource.onboardingComplete(requestOnboardingDto = onboardingEntity.toOnboardingInfoDto())
            val responseData = response.data
            responseData
        }
}