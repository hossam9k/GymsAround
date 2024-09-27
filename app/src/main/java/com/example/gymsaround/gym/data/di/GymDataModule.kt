package com.example.gymsaround.gym.data.di

import android.content.Context
import androidx.room.Room
import com.example.gymsaround.gym.data.local.GymsDao
import com.example.gymsaround.gym.data.local.GymsDatabase
import com.example.gymsaround.gym.data.remote.GymsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GymDataModule {

    @Provides
    fun provideApiService(retrofit: Retrofit): GymsApiService {
        return retrofit.create(GymsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://cairo-gyms-e62dd-default-rtdb.firebaseio.com/")
            .build()
    }

    @Provides
    fun provideRoomDao(
        db: GymsDatabase,
    ): GymsDao {
        return db.dao
    }

    @Singleton
    @Provides
    fun provideRoomDataBase(
        @ApplicationContext context: Context,
    ): GymsDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = GymsDatabase::class.java,
            name = "gyms_database"
        ).fallbackToDestructiveMigrationFrom()
            .build()
    }
}