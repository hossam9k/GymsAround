package com.example.gymsaround.gym.presentaion.details

import com.example.gymsaround.gym.data.local.LocalGym
import com.example.gymsaround.gym.domain.Gym

fun LocalGym.toGym(): Gym {
    // Map properties from LocalGym to Gym
    return Gym(
        id = id,
        name = name,
        place = place,
        isOpen = isOpen,
        isFavourite = isFavourite,
    )
}