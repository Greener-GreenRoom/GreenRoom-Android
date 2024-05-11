package com.greener.domain.model.home

import com.greener.domain.model.asset.BackgroundAccessory
import com.greener.domain.model.asset.PlantAccessory
import com.greener.domain.model.asset.PlantShape

data class PlantDecorationInfo(
    val shape : PlantShape = PlantShape.Main_Character,
    val glasses: PlantAccessory = PlantAccessory.empty,
    val hairAccessory : PlantAccessory = PlantAccessory.empty,
    val backgroundWindow : BackgroundAccessory = BackgroundAccessory.Frame,
    val backgroundShelf : BackgroundAccessory = BackgroundAccessory.Frame
)
