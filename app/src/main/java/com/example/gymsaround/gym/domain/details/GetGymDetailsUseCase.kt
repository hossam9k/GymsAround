package com.example.gymsaround.gym.domain.details

import com.example.gymsaround.gym.data.GymsRepository
import com.example.gymsaround.gym.domain.Gym
import javax.inject.Inject

class GetGymDetailsUseCase @Inject constructor(
    private val gymsRepository: GymsRepository
) {
    suspend operator fun invoke(gymId: Int): Gym {
        return gymsRepository.getGym(gymId)
    }
}