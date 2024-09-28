package com.example.gymsaround.gym.data.datasource


import com.example.gymsaround.gym.data.remote.GymsApiService
import com.example.gymsaround.gym.data.remote.RemoteGym
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: GymsApiService
) {
    suspend fun fetchGyms(): List<RemoteGym> = apiService.getGyms()
}