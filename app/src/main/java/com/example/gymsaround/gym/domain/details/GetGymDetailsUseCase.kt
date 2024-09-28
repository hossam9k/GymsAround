package com.example.gymsaround.gym.domain.details

import com.example.gymsaround.gym.data.GymsRepositoryImpl
import com.example.gymsaround.gym.domain.Gym
import com.example.gymsaround.gym.domain.GymsRepository
import javax.inject.Inject

class GetGymDetailsUseCase @Inject constructor(
    private val gymsRepository: GymsRepository
) {
    suspend operator fun invoke(gymId: Int): Gym {
        return gymsRepository.getGym(gymId)
    }
}