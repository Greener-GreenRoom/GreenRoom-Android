package com.greener.domain.model.asset

data class PlantAccessoryInfo(
    val id: Int,
    val itemType: PlantAccessoryType,
    val plantAccessory: PlantAccessory,
    val limitLevel: Int,
    val drawableID: Int,
    val isChecked: Boolean = false
)
