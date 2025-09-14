package org.android.bbangzip.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.bbangzip.data.service.DummyService
import org.android.bbangzip.data.service.TimerService
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
