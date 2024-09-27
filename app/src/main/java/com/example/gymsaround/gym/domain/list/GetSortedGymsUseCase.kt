package com.example.gymsaround.gym.domain.list

import com.example.gymsaround.gym.data.GymsRepository
import com.example.gymsaround.gym.domain.Gym
import javax.inject.Inject


class GetSortedGymsUseCase @Inject constructor(
    private val gymsRepository: GymsRepository,
){

    suspend operator fun invoke(): List<Gym> {
        return gymsRepository.getGyms()
    }
}