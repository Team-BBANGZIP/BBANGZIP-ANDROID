package org.android.bbangzip.data.auth.di

import com.kakao.sdk.user.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object KakaoModule {
    @Provides
    fun providesUserApiClient(): UserApiClient = UserApiClient.Companion.instance
}
