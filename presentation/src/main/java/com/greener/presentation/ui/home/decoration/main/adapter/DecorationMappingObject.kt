package com.greener.presentation.ui.home.decoration.main.adapter

import com.greener.domain.model.asset.AssetDetailTypeInfo
import com.greener.domain.model.asset.AssetType
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.domain.model.asset.PlantShapeType
import com.greener.presentation.model.decoration.AllAssetViewItem
import com.greener.presentation.model.decoration.AllAssetViewObject
import com.greener.presentation.model.decoration.AssetViewItem
import com.greener.presentation.model.decoration.AssetViewObject

object DecorationMappingObject {
    fun AssetDetailTypeInfo.toAllPlantShapeAssetViewItem(
        target: PlantShapeInfo,
        plantShapeList: List<PlantShapeInfo> = emptyList()
    ): AllAssetViewItem =
        AllAssetViewItem(
            AssetType.PLANT_SHAPE,
            AllAssetViewObject.AllPlantShapeObject(
                plantShapeType = PlantShapeType.valueOf(this.type),
                plantShapeTypeCode = this.typeCode,
                infoList = plantShapeList
                    .filter { it.plantShapeType.name == this.type }
                    .map { info -> info.updateChecked(target.id) }
            )
        )

    fun PlantShapeInfo.toPlantShapeAsserViewItem(
        target: PlantShapeInfo,
    ): AssetViewItem =
        AssetViewItem(
            AssetType.PLANT_SHAPE,
            AssetViewObject.PlantShapeObject(
                this.updateChecked(target.id)
            )
        )

    private fun PlantShapeInfo.updateChecked(targetId: Int): PlantShapeInfo =
        PlantShapeInfo(
            this.id,
            this.plantShapeType,
            this.plantShape,
            this.drawableID,
            this.id == targetId
        )
}

