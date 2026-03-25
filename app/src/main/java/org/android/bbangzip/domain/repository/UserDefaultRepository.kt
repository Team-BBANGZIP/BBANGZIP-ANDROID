package org.android.bbangzip.domain.repository

import kotlinx.coroutines.flow.Flow
import org.android.bbangzip.UserPreferences

interface UserDefaultRepository {
    val userPreferenceFlow: Flow<UserPreferences>

    suspend fun setAccessToken(accessToken: String)

    suspend fun clearAccessToken()

    suspend fun setRefreshToken(refreshToken: String)

    suspend fun clearRefreshToken()

    suspend fun setIsLogin(isLogin: Boolean)

    suspend fun setIsSundayStart(isSundayStart: Boolean)
}
