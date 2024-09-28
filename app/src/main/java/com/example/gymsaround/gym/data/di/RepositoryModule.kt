package com.example.gymsaround.gym.data.di

import com.example.gymsaround.gym.data.GymsRepositoryImpl
import com.example.gymsaround.gym.domain.GymsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(
        gymsRepositoryImpl: GymsRepositoryImpl
    ): GymsRepository
}