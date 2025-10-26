package org.android.bbangzip.data.datasource.remote.service.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.bbangzip.data.auth.qualifier.BbangZip
import org.android.bbangzip.data.datasource.remote.service.CategoryService
import org.android.bbangzip.data.datasource.remote.service.DummyService
import org.android.bbangzip.data.datasource.remote.service.TimerService
import org.android.bbangzip.data.datasource.remote.service.TodoService
import org.android.bbangzip.data.datasource.remote.service.UserService
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

    @Provides
    @Singleton
    fun provideTodoService(
        @BbangZip retrofit: Retrofit,
    ): TodoService = retrofit.create(TodoService::class.java)

    @Provides
    @Singleton
    fun provideUserService(
        @BbangZip retrofit: Retrofit,
    ): UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideCategoryService(
        @BbangZip retrofit: Retrofit,
    ): CategoryService = retrofit.create(CategoryService::class.java)
}
