package org.android.bbangzip.data.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.bbangzip.data.repository.local.UserRepositoryImpl
import org.android.bbangzip.data.repository.remote.DummyRepositoryImpl
import org.android.bbangzip.data.repository.remote.TimerRepositoryImpl
import org.android.bbangzip.data.repository.remote.TodoRepositoryImpl
import org.android.bbangzip.domain.repository.DummyRepository
import org.android.bbangzip.domain.repository.TimerRepository
import org.android.bbangzip.domain.repository.TodoRepository
import org.android.bbangzip.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsUserRepository(repositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindsDummyRepository(repositoryImpl: DummyRepositoryImpl): DummyRepository

    @Binds
    @Singleton
    abstract fun bindsTimerRepository(repositoryImpl: TimerRepositoryImpl): TimerRepository

    @Binds
    @Singleton
    abstract fun bindsTodoRepository(repositoryImpl: TodoRepositoryImpl): TodoRepository
}
