package com.greener.data.repository

import android.content.Context
import android.util.Log
import com.greener.data.source.AssetDataSource
import com.greener.domain.model.asset.AssetDetailTypeInfo
import com.greener.domain.model.asset.AssetType
import com.greener.domain.model.asset.BackgroundAccessoryInfo
import com.greener.domain.model.asset.BackgroundAccessory
import com.greener.domain.model.asset.BackgroundAccessoryType
import com.greener.domain.model.asset.PlantAccessoryInfo
import com.greener.domain.model.asset.PlantAccessory
import com.greener.domain.model.asset.PlantAccessoryType
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.domain.model.asset.PlantShape
import com.greener.domain.model.asset.PlantShapeType
import com.greener.domain.repository.AssetRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AssetRepositoryImpl @Inject constructor(
    private val dataSource: AssetDataSource,
    @ApplicationContext private val context: Context,
) : AssetRepository {
    override suspend fun getAllPlantShapeAsset(): List<PlantShapeInfo> {
        val list = dataSource.getAllPlantShapes()

        return list.map {
            PlantShapeInfo(
                id = it.id,
                plantShapeType = initPlantShapeType(it.plantType),
                plantShape = initPlantShapeName(it.plantName),
                drawableID = getDrawableId(0, it.plantName),
            )
        }
    }

    override suspend fun getAllPlantAccessoryAsset(): List<PlantAccessoryInfo> {
        val list = dataSource.getAllPlantAccessory()

        return list.map {
            PlantAccessoryInfo(
                id = it.id,
                itemType = initPlantAccessoryType(it.itemType.uppercase()),
                plantAccessory = initPlantAccessoryName(it.itemName),
                limitLevel = it.limitLevel,
                drawableID = when (initPlantAccessoryType(it.itemType.uppercase())) {
                    PlantAccessoryType.EYE -> {
                        getDrawableId(PLANT_ACCESSORY_EYE, it.itemName)
                    }

                    PlantAccessoryType.FACE -> {
                        getDrawableId(PLANT_ACCESSORY_FACE, it.itemName)
                    }

                    PlantAccessoryType.HEAD -> {
                        getDrawableId(PLANT_ACCESSORY_HEAD, it.itemName)
                    }
                },
            )
        }.sortedBy { it.limitLevel }
    }

    override suspend fun getAllBackgroundAccessoryAsset(): List<BackgroundAccessoryInfo> {
        val list = dataSource.getAllBackgroundAccessory()

        return list.map {
            BackgroundAccessoryInfo(
                id = it.id,
                itemType = initBackgroundAccessoryType(it.itemType.uppercase()),
                backgroundAccessory = initBackgroundAccessoryName(it.itemName),
                limitLevel = it.limitLevel,
                drawableID = when (initBackgroundAccessoryType(it.itemType.uppercase())) {
                    BackgroundAccessoryType.OTHER -> {
                        getDrawableId(BACKGROUND_ACCESSORY_OTHER, it.itemName)
                    }

                    BackgroundAccessoryType.GLASS -> {
                        getDrawableId(BACKGROUND_ACCESSORY_GLASS, it.itemName)
                    }

                    BackgroundAccessoryType.SHELF -> {
                        getDrawableId(BACKGROUND_ACCESSORY_SHELF, it.itemName)
                    }
                },
            )
        }.sortedBy { it.limitLevel }
    }

    override suspend fun getAssetDetailType(assetType: AssetType): List<AssetDetailTypeInfo> {
        val assetDetailList = emptyList<AssetDetailTypeInfo>().toMutableList()
        when (assetType) {
            AssetType.PLANT_SHAPE -> {
                val types = dataSource.getAllPlantShapes().map { it.plantType }.distinct()
                types.forEachIndexed { index, type ->
                    assetDetailList.add(
                        AssetDetailTypeInfo(
                            id = index + 1,
                            assetType = AssetType.PLANT_SHAPE,
                            type = initPlantShapeType(type.uppercase()).name,
                            typeCode = getStringId(type.uppercase())
                        )
                    )
                }
            }
            AssetType.PLANT_ACCESSORY -> {
                val types = dataSource.getAllPlantAccessory().map { it.itemType }.distinct()
                types.forEachIndexed { index, type ->
                    assetDetailList.add(
                        AssetDetailTypeInfo(
                            id = index + 1,
                            assetType = AssetType.PLANT_ACCESSORY,
                            type = initPlantAccessoryType(type.uppercase()).name,
                            typeCode = getStringId(type.uppercase())
                        )
                    )
                }
            }
            AssetType.BACKGROUND_ACCESSORY -> {
                val types = dataSource.getAllBackgroundAccessory().map { it.itemType }.distinct()
                types.forEachIndexed { index, type ->
                    if (index == 2) {
                        return@forEachIndexed
                    }

                    assetDetailList.add(
                        AssetDetailTypeInfo(
                            id = index + 1,
                            assetType = AssetType.BACKGROUND_ACCESSORY,
                            type = initBackgroundAccessoryType(type.uppercase()).name,
                            typeCode = getStringId(type.uppercase())
                        )
                    )
                }
            }
        }


        return assetDetailList
    }

    private fun initPlantShapeType(plantShape: String): PlantShapeType =
        when (plantShape) {
            "FOLIAGE" -> PlantShapeType.FOLIAGE
            "SUCCEULENT_CACTUS" -> PlantShapeType.SUCCEULENT_CACTUS
            "ANNUAL" -> PlantShapeType.ANNUAL
            "VINE" -> PlantShapeType.VINE
            "BULB" -> PlantShapeType.BULB
            "HERB" -> PlantShapeType.HERB
            "EPIPHYTE" -> PlantShapeType.EPIPHYTE
            "TUBEROUS" -> PlantShapeType.TUBEROUS
            else -> PlantShapeType.OTHER
        }

    private fun initPlantShapeName(plantShapeName: String): PlantShape =
        PlantShape.valueOf(plantShapeName)

    private fun initPlantAccessoryType(plantAccessoryType: String): PlantAccessoryType =
        PlantAccessoryType.valueOf(plantAccessoryType)

    private fun initPlantAccessoryName(plantAccessoryName: String): PlantAccessory =
        PlantAccessory.valueOf(plantAccessoryName)

    private fun initBackgroundAccessoryType(backgroundAccessoryType: String): BackgroundAccessoryType =
        BackgroundAccessoryType.valueOf(backgroundAccessoryType)

    private fun initBackgroundAccessoryName(backgroundAccessoryName: String): BackgroundAccessory =
        BackgroundAccessory.valueOf(backgroundAccessoryName)

    private fun getDrawableId(itemType: Int, itemName: String): Int {
        val lowerName = itemName.lowercase()

        return when (itemType) {
            PLANT_SHAPE -> {
                context.resources.getIdentifier(
                    ASSET + PLANT + lowerName,
                    DRAWABLE,
                    context.packageName,
                )
            }

            PLANT_ACCESSORY_FACE -> {
                context.resources.getIdentifier(
                    ASSET + FACE + lowerName,
                    DRAWABLE,
                    context.packageName,
                )
            }

            PLANT_ACCESSORY_HEAD -> {
                context.resources.getIdentifier(
                    ASSET + HEAD + lowerName,
                    DRAWABLE,
                    context.packageName,
                )
            }

            PLANT_ACCESSORY_EYE -> {
                context.resources.getIdentifier(
                    ASSET + EYE + lowerName,
                    DRAWABLE,
                    context.packageName,
                )
            }

            BACKGROUND_ACCESSORY_GLASS -> {
                context.resources.getIdentifier(
                    ASSET + GLASS + lowerName,
                    DRAWABLE,
                    context.packageName,
                )
            }

            BACKGROUND_ACCESSORY_SHELF -> {
                context.resources.getIdentifier(
                    ASSET + SHELF + lowerName,
                    DRAWABLE,
                    context.packageName,
                )
            }

            BACKGROUND_ACCESSORY_OTHER -> {
                context.resources.getIdentifier(
                    ASSET + OTHER + lowerName,
                    DRAWABLE,
                    context.packageName,
                )
            }

            else -> {
                0
            }
        }
    }

    private fun getStringId(itemName: String): Int =
        context.resources.getIdentifier(
            itemName.lowercase(),
            STRING,
            context.packageName,
        )

    companion object {
        const val PLANT_SHAPE = 0
        const val PLANT_ACCESSORY_EYE = 1
        const val PLANT_ACCESSORY_HEAD = 2
        const val PLANT_ACCESSORY_FACE = 3
        const val BACKGROUND_ACCESSORY_GLASS = 4
        const val BACKGROUND_ACCESSORY_SHELF = 5
        const val BACKGROUND_ACCESSORY_OTHER = 6

        const val DRAWABLE = "drawable"
        const val STRING = "string"

        const val ASSET = "asset_"
        const val PLANT = "plant_"
        const val EYE = "eye_"
        const val HEAD = "head_"
        const val FACE = "face_"
        const val GLASS = "glass_"
        const val SHELF = "shelf_"
        const val OTHER = "other_"
    }
}
