package org.android.bbangzip.domain.repository

import org.android.bbangzip.domain.model.OnboardingInfo
import org.android.bbangzip.domain.model.ProfileInformation
import org.android.bbangzip.domain.model.ReissueToken
import org.android.bbangzip.domain.model.UserTokenInfo

interface UserRepository {
    suspend fun login(code: String): Result<UserTokenInfo>

    suspend fun reissue(): Result<ReissueToken>

    suspend fun logout(): Result<Unit>

    suspend fun withdraw(): Result<Unit>

    suspend fun onboardingComplete(onboardingEntity: OnboardingInfo): Result<Unit>

    suspend fun getProfileInformation(): Result<ProfileInformation>

    suspend fun modifyProfileInformation(
        profileImageKey: Int,
        nickname: String,
        commitmentMessage: String,
    ): Result<Unit>
}
