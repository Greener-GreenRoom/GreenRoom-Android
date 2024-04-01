package com.greener.presentation.model.decoration

import com.greener.domain.model.asset.BackgroundAccessoryInfo
import com.greener.domain.model.asset.BackgroundAccessoryType
import com.greener.domain.model.asset.PlantAccessoryInfo
import com.greener.domain.model.asset.PlantAccessoryType
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.domain.model.asset.PlantShapeType

sealed class AssetViewObject {
    data class PlantShapeObject(
        val infoList: PlantShapeInfo
    ): AssetViewObject()

    data class PlantAccessoriesObject(
        val infoList: PlantAccessoryInfo
    ): AssetViewObject()

    data class BackgroundAccessoriesObject(
        val infoList: BackgroundAccessoryInfo
    ): AssetViewObject()
}
