package com.greener.domain.repository

import com.greener.domain.model.asset.BackgroundAccessoryInfo
import com.greener.domain.model.asset.PlantAccessoryInfo
import com.greener.domain.model.asset.PlantShapeInfo

interface AssetRepository {

    suspend fun getAllPlantShapeAsset(): List<PlantShapeInfo>
    suspend fun getAllPlantAccessoryAsset(): List<PlantAccessoryInfo>
    suspend fun getAllBackgroundAccessoryAsset(): List<BackgroundAccessoryInfo>
}
