package com.example.gymsaround.gym.domain.list

import com.example.gymsaround.gym.data.GymsRepositoryImpl
import com.example.gymsaround.gym.domain.Gym
import com.example.gymsaround.gym.domain.GymsRepository
import javax.inject.Inject


class GetInitialGymsUseCase @Inject constructor(
    private val gymsRepository: GymsRepository,
    private val getSortedGymsUseCase: GetSortedGymsUseCase,
) {


    suspend operator fun invoke(): List<Gym> {
        gymsRepository.loadGyms()
        return getSortedGymsUseCase()
    }
}