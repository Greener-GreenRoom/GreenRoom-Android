package com.greener.presentation.ui.home.decoration.main

import com.greener.domain.model.asset.AssetDetailTypeInfo
import com.greener.domain.model.asset.AssetType
import com.greener.domain.model.asset.BackgroundAccessoryInfo
import com.greener.domain.model.asset.BackgroundAccessoryType
import com.greener.domain.model.asset.PlantAccessoryInfo
import com.greener.domain.model.asset.PlantAccessoryType
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

    fun AssetDetailTypeInfo.toAllPlantAccessoryAssetViewItem(
        target: PlantAccessoryInfo,
        other: PlantAccessoryInfo,
        plantAccessoryList: List<PlantAccessoryInfo> = emptyList()
    ): AllAssetViewItem =
        AllAssetViewItem(
            AssetType.PLANT_ACCESSORY,
            AllAssetViewObject.AllPlantAccessoriesObject(
                plantAccessoryType = PlantAccessoryType.valueOf(this.type),
                plantAccessoryTypeCode = this.typeCode,
                infoList = plantAccessoryList
                    .filter { it.itemType.name == this.type }
                    .map { info -> info.updateChecked(target.id, other.id) }
            )
        )

    fun PlantAccessoryInfo.toPlantAccessoryViewItem(
        target: PlantAccessoryInfo,
        other: PlantAccessoryInfo
    ): AssetViewItem =
        AssetViewItem(
            AssetType.PLANT_ACCESSORY,
            AssetViewObject.PlantAccessoriesObject(
                this.updateChecked(target.id, other.id)
            )
        )

    fun AssetDetailTypeInfo.toAllBackgroundAccessoryAssetViewItem(
        target: BackgroundAccessoryInfo,
        other: BackgroundAccessoryInfo,
        backgroundAccessoryList: List<BackgroundAccessoryInfo> = emptyList()
    ) : AllAssetViewItem =
        AllAssetViewItem(
            AssetType.BACKGROUND_ACCESSORY,
            AllAssetViewObject.AllBackgroundAccessoriesObject(
                backgroundAccessorType = BackgroundAccessoryType.valueOf(this.type),
                backgroundAccessoryTypeCode = this.typeCode,
                infoList = backgroundAccessoryList
                    .filter { it.itemType.name == this.type }
                    .map { info -> info.updateChecked(target.id, other.id) }
            )
        )

    fun BackgroundAccessoryInfo.toBackfroundAccessoryViewItem(
        target: BackgroundAccessoryInfo,
        other: BackgroundAccessoryInfo
    ): AssetViewItem =
        AssetViewItem(
            AssetType.BACKGROUND_ACCESSORY,
            AssetViewObject.BackgroundAccessoriesObject(
                this.updateChecked(target.id, other.id)
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

    private fun PlantAccessoryInfo.updateChecked(glassesId: Int, hairAccessoryId: Int): PlantAccessoryInfo =
        PlantAccessoryInfo(
            this.id,
            this.itemType,
            this.plantAccessory,
            this.limitLevel,
            this.drawableID,
            this.id == glassesId || this.id == hairAccessoryId
        )

    private fun BackgroundAccessoryInfo.updateChecked(shelfId: Int, windowId: Int): BackgroundAccessoryInfo =
        BackgroundAccessoryInfo(
            this.id,
            this.itemType,
            this.backgroundAccessory,
            this.limitLevel,
            this.drawableID,
            this.viewDrawableId,
            this.id == shelfId || this.id == windowId
        )
}

