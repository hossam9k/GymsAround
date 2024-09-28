package com.example.gymsaround.gym.domain

interface GymsRepository {

    suspend fun toggleFavouriteGym(gymId: Int, state: Boolean): List<Gym>

    suspend fun loadGyms()

    suspend fun getGyms(): List<Gym>

    suspend fun updateLocalDatabase()

    suspend fun getGym(gymId: Int): Gym
}