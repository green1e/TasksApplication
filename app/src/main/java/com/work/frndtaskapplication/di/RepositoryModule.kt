package com.work.frndtaskapplication.di

import com.work.frndtaskapplication.data.repository.TasksRepository
import com.work.frndtaskapplication.data.repository.TasksRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideStackRepository(tasksRepositoryImpl: TasksRepositoryImpl): TasksRepository
}
