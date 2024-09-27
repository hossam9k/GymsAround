package com.example.gymsaround.gym.domain

interface IGymsRepository {

    suspend fun toggleFavouriteGym(gymId: Int, state: Boolean): List<Gym>

    suspend fun loadGyms()

    suspend fun getGyms(): List<Gym>

    suspend fun updateLocalDatabase()
}