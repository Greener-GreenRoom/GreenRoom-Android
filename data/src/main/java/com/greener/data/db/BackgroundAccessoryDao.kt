package com.greener.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BackgroundAccessoryDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addBackgroundAccessory(item: BackgroundAccessoryEntity)

    @Query("SELECT * FROM BackgroundAccessoryEntity")
    suspend fun getAllBackgroundAccessory(): List<BackgroundAccessoryEntity>
}
