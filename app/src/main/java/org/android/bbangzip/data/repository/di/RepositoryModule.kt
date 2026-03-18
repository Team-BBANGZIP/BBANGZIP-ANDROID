package org.android.bbangzip.data.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.bbangzip.data.repository.fake.FakeCategoryRepository
import org.android.bbangzip.data.repository.fake.FakeCommitmentRepository
import org.android.bbangzip.data.repository.fake.FakeDummyRepository
import org.android.bbangzip.data.repository.fake.FakeTimerRepository
import org.android.bbangzip.data.repository.fake.FakeTodoRepository
import org.android.bbangzip.data.repository.fake.FakeUserRepository
import org.android.bbangzip.data.repository.local.UserDefaultRepositoryImpl
import org.android.bbangzip.domain.repository.CategoryRepository
import org.android.bbangzip.domain.repository.CommitmentRepository
import org.android.bbangzip.domain.repository.DummyRepository
import org.android.bbangzip.domain.repository.TimerRepository
import org.android.bbangzip.domain.repository.TodoRepository
import org.android.bbangzip.domain.repository.UserDefaultRepository
import org.android.bbangzip.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsUserDefaultRepository(repositoryImpl: UserDefaultRepositoryImpl): UserDefaultRepository

    @Binds
    @Singleton
    abstract fun bindsUserRepository(repositoryImpl: FakeUserRepository): UserRepository

    @Binds
    @Singleton
    abstract fun bindsDummyRepository(repositoryImpl: FakeDummyRepository): DummyRepository

    @Binds
    @Singleton
    abstract fun bindsTimerRepository(repositoryImpl: FakeTimerRepository): TimerRepository

    @Binds
    @Singleton
    abstract fun bindsTodoRepository(repositoryImpl: FakeTodoRepository): TodoRepository

    @Binds
    @Singleton
    abstract fun bindsCommitmentRepository(repositoryImpl: FakeCommitmentRepository): CommitmentRepository

    @Binds
    @Singleton
    abstract fun bindsCategoryRepository(repositoryImpl: FakeCategoryRepository): CategoryRepository
}
