package com.example.gymsaround.gym.data.remote

import com.google.gson.annotations.SerializedName

data class RemoteGym(
    val id: Int,
    @SerializedName("gym_name")
    val name: String,
    @SerializedName("gym_location")
    val place: String,
    @SerializedName("isOpen")
    val isOpen: Boolean,
)