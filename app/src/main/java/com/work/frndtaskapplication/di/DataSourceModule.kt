package com.work.frndtaskapplication.di

import com.work.frndtaskapplication.data.network.TasksService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun provideRemoteDataSourceInstance(): TasksService {
        return Retrofit.Builder()
            .baseUrl("https://dev.frndapp.in:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}