package com.example.gymsaround

import com.example.gymsaround.gym.domain.Gym

object DummyGymsList {
    fun getDomainDummyGymsList() = buildList {
        add(Gym(0, "n0", "p0", false))
        add(Gym(1, "n1", "p1", false))
        add(Gym(2, "n2", "p2", false))
        add(Gym(3, "n3", "p3", false))
        add(Gym(4, "n4", "p4", false))
    }
}