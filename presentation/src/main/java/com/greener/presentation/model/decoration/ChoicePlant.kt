package com.greener.presentation.model.decoration

import com.greener.domain.model.asset.PlantAccessory
import com.greener.domain.model.asset.PlantShape

data class ChoicePlant(
    val plantShape : PlantShape = PlantShape.Main_Character,
    val headAccessory : PlantAccessory? = null,
    val eyeAccessory : PlantAccessory? = null
)
