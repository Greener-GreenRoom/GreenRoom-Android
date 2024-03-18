package com.greener.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlantAccessoryDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addPlantAccessory(item: PlantAccessoryEntity)

    @Query("SELECT * FROM PlantAccessoryEntity")
    suspend fun getAllPlantAccessory(): List<PlantAccessoryEntity>
}
