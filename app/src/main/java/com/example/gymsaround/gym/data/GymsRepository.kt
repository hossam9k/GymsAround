package com.example.gymsaround.gym.data

import com.example.gymsaround.gym.data.di.IODispatcher
import com.example.gymsaround.gym.data.local.GymsDao
import com.example.gymsaround.gym.data.local.LocalGym
import com.example.gymsaround.gym.data.local.LocalGymFavouriteState
import com.example.gymsaround.gym.data.remote.GymsApiService
import com.example.gymsaround.gym.domain.Gym
import com.example.gymsaround.gym.presentaion.details.toGym
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsRepository @Inject constructor(
    private val apiService: GymsApiService,
    private val gymsDao: GymsDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun toggleFavouriteGym(gymId: Int, state: Boolean) =
        withContext(dispatcher) {
            gymsDao.update(LocalGymFavouriteState(gymId, state))
            gymsDao.getAll().map {
                Gym(
                    it.id,
                    it.name,
                    it.place,
                    it.isOpen,
                    it.isFavourite
                )
            }
        }

    suspend fun loadGyms() = withContext(dispatcher) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (gymsDao.getAll().isEmpty()) {
                throw Exception("Something went wrong, No data was found, Try connecting to Internet.")
            }
        }
    }

    suspend fun getGyms(): List<Gym> {
        return withContext(dispatcher) {
            return@withContext gymsDao.getAll().map {
                Gym(
                    it.id,
                    it.name,
                    it.place,
                    it.isOpen,
                    it.isFavourite
                )
            }
        }
    }

    suspend fun getGym(gymId: Int): Gym{
        return gymsDao.getGym(gymId).toGym()
    }

    private suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favouriteGymsList = gymsDao.getFavouriteGyms()
        gymsDao.addAll(
            gyms.map {
                LocalGym(
                    it.id,
                    it.name,
                    it.place,
                    it.isOpen,
                )
            })
        gymsDao.updateAll(
            favouriteGymsList.map { LocalGymFavouriteState(it.id, true) }
        )
    }

}