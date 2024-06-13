package com.greener.domain.model.home

import com.greener.domain.model.asset.BackgroundAccessory
import com.greener.domain.model.asset.PlantAccessory
import com.greener.domain.model.asset.PlantShape
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.domain.usecase.asset.GetPlantShapeListUseCase
import javax.inject.Inject

data class PlantDecorationInfo(
    val shape : PlantShape = PlantShape.Main_Character,
    val glasses: PlantAccessory = PlantAccessory.empty,
    val hairAccessory : PlantAccessory = PlantAccessory.empty,
    val backgroundWindow : BackgroundAccessory = BackgroundAccessory.Frame,
    val backgroundShelf : BackgroundAccessory = BackgroundAccessory.Frame
)
