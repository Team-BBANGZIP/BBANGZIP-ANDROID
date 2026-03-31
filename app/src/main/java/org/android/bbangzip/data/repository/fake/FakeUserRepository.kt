package org.android.bbangzip.data.repository.fake

import org.android.bbangzip.domain.model.OnboardingInfo
import org.android.bbangzip.domain.model.ReissueToken
import org.android.bbangzip.domain.model.UserTokenInfo
import org.android.bbangzip.domain.repository.UserRepository
import javax.inject.Inject

class FakeUserRepository
    @Inject
    constructor() : UserRepository {
        override suspend fun login(code: String): Result<UserTokenInfo> {
            return Result.success(
                UserTokenInfo(
                    accessToken = "fake_access_token",
                    refreshToken = "fake_refresh_token",
                    isSignUpComplete = false,
                ),
            )
        }

        override suspend fun reissue(): Result<ReissueToken> {
            return Result.success(
                ReissueToken(
                    accessToken = "new_fake_access_token",
                    refreshToken = "new_fake_refresh_token",
                ),
            )
        }

        override suspend fun logout(): Result<Unit> {
            return Result.success(Unit)
        }

        override suspend fun withdraw(): Result<Unit> {
            return Result.success(Unit)
        }

        override suspend fun onboardingComplete(onboardingEntity: OnboardingInfo): Result<Unit> {
            return Result.success(Unit)
        }
    }
