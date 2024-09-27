package com.example.gymsaround.gym.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalGym::class],
    version = 1,
    exportSchema = false
)
abstract class GymsDatabase : RoomDatabase() {

    abstract val dao: GymsDao


}
