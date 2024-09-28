package com.example.gymsaround.gym.domain.list

import com.example.gymsaround.gym.data.GymsRepositoryImpl
import com.example.gymsaround.gym.domain.Gym
import com.example.gymsaround.gym.domain.GymsRepository
import javax.inject.Inject


class ToggleFavouriteStateUseCase @Inject constructor(
    private val gymsRepository: GymsRepository,
    private val getSortedGymsUseCase: GetSortedGymsUseCase,
) {
    suspend operator fun invoke(id: Int, oldState: Boolean): List<Gym> {
        val newState = oldState.not()
        gymsRepository.toggleFavouriteGym(id, newState).sortedBy { it.name }
        return getSortedGymsUseCase()
    }
}