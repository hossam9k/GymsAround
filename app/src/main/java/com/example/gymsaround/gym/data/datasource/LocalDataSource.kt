package com.example.gymsaround.gym.data.datasource

import com.example.gymsaround.gym.data.local.GymsDao
import com.example.gymsaround.gym.data.local.LocalGym
import com.example.gymsaround.gym.data.local.LocalGymFavouriteState
import javax.inject.Inject


class LocalDataSource @Inject constructor(
    private val gymsDao: GymsDao
) {
    suspend fun getAllGyms(): List<LocalGym> = gymsDao.getAll()

    suspend fun getGymById(gymId: Int): LocalGym = gymsDao.getGym(gymId)

    suspend fun updateGymFavouriteState(gymId: Int, isFavourite: Boolean) {
        gymsDao.update(LocalGymFavouriteState(gymId, isFavourite))
    }

    suspend fun getFavouriteGyms(): List<LocalGym> = gymsDao.getFavouriteGyms()

    suspend fun addAllGyms(gyms: List<LocalGym>) {
        gymsDao.addAll(gyms)
    }

    suspend fun updateFavouriteGyms(favouriteGyms: List<LocalGymFavouriteState>) {
        gymsDao.updateAll(favouriteGyms)
    }
}