package org.android.bbangzip.data.repository.local

import kotlinx.coroutines.flow.Flow
import org.android.bbangzip.UserPreferences
import org.android.bbangzip.data.source.local.datasource.UserLocalDataSource
import org.android.bbangzip.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val userDataSource: UserLocalDataSource,
    ) : UserRepository {
        override val userPreferenceFlow: Flow<UserPreferences> = userDataSource.userPreferencesFlow

        override suspend fun setAccessToken(accessToken: String) {
            userDataSource.updateUserPreferences { userData ->
                userData
                    .toBuilder()
                    .setAccessToken(accessToken)
                    .build()
            }
        }

        override suspend fun clearAccessToken() {
            userDataSource.updateUserPreferences { userData ->
                val cleared =
                    userData
                        .toBuilder()
                        .clearAccessToken()
                        .build()

                cleared
            }
        }

        override suspend fun setRefreshToken(refreshToken: String) {
            userDataSource.updateUserPreferences { userData ->
                userData
                    .toBuilder()
                    .setRefreshToken(refreshToken)
                    .build()
            }
        }

        override suspend fun clearRefreshToken() {
            userDataSource.updateUserPreferences { userData ->
                userData
                    .toBuilder()
                    .clearRefreshToken()
                    .build()
            }
        }

        override suspend fun setIsLogin(isLogin: Boolean) {
            userDataSource.updateUserPreferences { userData ->
                userData
                    .toBuilder()
                    .setIsLogin(isLogin)
                    .build()
            }
        }
    }
