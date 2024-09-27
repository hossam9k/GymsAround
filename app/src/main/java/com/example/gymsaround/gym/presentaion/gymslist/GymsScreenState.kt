package com.example.gymsaround.gym.presentaion.gymslist

import com.example.gymsaround.gym.domain.Gym

data class GymsScreenState(
    val gyms: List<Gym>,
    val isLoading: Boolean,
    val error: String? = null,
)