package com.greener.domain.model.asset

data class PlantAccessoryInfo(
    val id: Int,
    val itemType: PlantAccessoryType,
    val plantAccessoryName: PlantAccessoryName,
    val drawableID: Int,
)
