package com.example.gymsaround.gym.data

import com.example.gymsaround.gym.data.datasource.LocalDataSource
import com.example.gymsaround.gym.data.datasource.RemoteDataSource
import com.example.gymsaround.gym.data.di.IODispatcher
import com.example.gymsaround.gym.data.local.LocalGym
import com.example.gymsaround.gym.data.local.LocalGymFavouriteState
import com.example.gymsaround.gym.domain.Gym
import com.example.gymsaround.gym.domain.GymsRepository
import com.example.gymsaround.gym.presentaion.details.toGym
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : GymsRepository {

    override suspend fun toggleFavouriteGym(gymId: Int, state: Boolean) =
        withContext(dispatcher) {
            localDataSource.updateGymFavouriteState(gymId, state)
            localDataSource.getAllGyms().map {
                it.toGym()
            }
        }

    override suspend fun loadGyms() = withContext(dispatcher) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (localDataSource.getAllGyms().isEmpty()) {
                throw Exception("Something went wrong, No data was found, Try connecting to Internet.")
            }
        }
    }

    override suspend fun getGyms(): List<Gym> {
        return withContext(dispatcher) {
            localDataSource.getAllGyms().map {
                it.toGym()
            }
        }
    }

    override suspend fun getGym(gymId: Int): Gym {
        return localDataSource.getGymById(gymId).toGym()
    }

    override suspend fun updateLocalDatabase() {
        val gyms = remoteDataSource.fetchGyms()
        val favouriteGymsList = localDataSource.getFavouriteGyms()
        localDataSource.addAllGyms(
            gyms.map {
                LocalGym(it.id, it.name, it.place, it.isOpen)
            }
        )
        localDataSource.updateFavouriteGyms(
            favouriteGymsList.map {
                LocalGymFavouriteState(it.id, true)
            }
        )
    }
}