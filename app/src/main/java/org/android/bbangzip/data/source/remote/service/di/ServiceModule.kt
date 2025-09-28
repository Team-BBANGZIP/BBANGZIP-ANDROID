package org.android.bbangzip.data.source.remote.service.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.bbangzip.data.source.remote.service.DummyService
import org.android.bbangzip.data.source.remote.service.TimerService
import org.android.bbangzip.data.network.auth.qualifier.BbangZip
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideDummyService(
        @BbangZip retrofit: Retrofit,
    ): DummyService = retrofit.create(DummyService::class.java)

    @Provides
    @Singleton
    fun provideTimerService(
        @BbangZip retrofit: Retrofit,
    ): TimerService = retrofit.create(TimerService::class.java)
}