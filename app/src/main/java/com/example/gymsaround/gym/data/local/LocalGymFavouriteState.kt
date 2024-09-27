package com.example.gymsaround.gym.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class LocalGymFavouriteState(
    @ColumnInfo("gym_id")
    val id: Int,
    @ColumnInfo("is_favourite")
    val isFavourite: Boolean = false
)