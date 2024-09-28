package com.example.gymsaround.gym.data.di

import com.example.gymsaround.gym.data.datasource.LocalDataSource
import com.example.gymsaround.gym.data.datasource.RemoteDataSource
import com.example.gymsaround.gym.data.local.GymsDao
import com.example.gymsaround.gym.data.remote.GymsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiService: GymsApiService
    ): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        gymsDao: GymsDao
    ): LocalDataSource {
        return LocalDataSource(gymsDao)
    }
}