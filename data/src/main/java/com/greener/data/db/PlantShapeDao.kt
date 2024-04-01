package com.greener.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlantShapeDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addPlantShape(item: PlantShapeEntity)

    @Query("SELECT * FROM PlantShapeEntity")
    suspend fun getAllPlantShapes(): List<PlantShapeEntity>
}
