package com.greener.presentation.model.decoration

import com.greener.domain.model.asset.BackgroundAccessoryInfo
import com.greener.domain.model.asset.BackgroundAccessoryType
import com.greener.domain.model.asset.PlantAccessoryInfo
import com.greener.domain.model.asset.PlantAccessoryType
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.domain.model.asset.PlantShapeType

sealed class AllAssetViewObject {
    data class AllPlantShapeObject(
        val plantShapeType: PlantShapeType,
        val plantShapeTypeCode: Int,
        val infoList: List<PlantShapeInfo>
    ): AllAssetViewObject()

    data class AllPlantAccessoriesObject(
        val plantAccessoryType: PlantAccessoryType,
        val plantAccessoryTypeCode: Int,
        val infoList: List<PlantAccessoryInfo>
    ): AllAssetViewObject()

    data class AllBackgroundAccessoriesObject(
        val backgroundAccessorType: BackgroundAccessoryType,
        val backgroundAccessoryTypeCode: Int,
        val infoList: List<BackgroundAccessoryInfo>
    ): AllAssetViewObject()
}
