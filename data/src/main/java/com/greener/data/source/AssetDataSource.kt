package com.greener.data.source

import com.greener.data.db.BackgroundAccessoryDatabase
import com.greener.data.db.BackgroundAccessoryEntity
import com.greener.data.db.PlantAccessoryDatabase
import com.greener.data.db.PlantAccessoryEntity
import com.greener.data.db.PlantShapeDatabase
import com.greener.data.db.PlantShapeEntity
import javax.inject.Inject

class AssetDataSource @Inject constructor(
    private val plantShapeDatabase: PlantShapeDatabase,
    private val plantAccessoryDatabase: PlantAccessoryDatabase,
    private val backgroundAccessoryDatabase: BackgroundAccessoryDatabase,
) {
    suspend fun getAllPlantShapes(): List<PlantShapeEntity> =
        plantShapeDatabase.plantShapeDao().getAllPlantShapes()

    suspend fun getAllPlantAccessory(): List<PlantAccessoryEntity> =
        plantAccessoryDatabase.plantAccessoryDao().getAllPlantAccessory()

    suspend fun getAllBackgroundAccessory(): List<BackgroundAccessoryEntity> =
        backgroundAccessoryDatabase.backgroundAccessoryDao().getAllBackgroundAccessory()
}
