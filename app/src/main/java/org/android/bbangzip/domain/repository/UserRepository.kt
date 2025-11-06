package org.android.bbangzip.domain.repository

import org.android.bbangzip.domain.model.OnboardingInfo
import org.android.bbangzip.domain.model.ReissueToken
import org.android.bbangzip.domain.model.UserTokenInfo

interface UserRepository {
    suspend fun login(code: String): Result<UserTokenInfo>

    suspend fun reissue(): Result<ReissueToken>

    suspend fun logout(): Result<String>

    suspend fun withdraw(): Result<String>

    suspend fun onboardingComplete(onboardingEntity: OnboardingInfo): Result<String>
}
