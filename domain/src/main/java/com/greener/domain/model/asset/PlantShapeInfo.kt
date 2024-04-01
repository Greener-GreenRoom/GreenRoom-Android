package com.greener.domain.model.asset

data class PlantShapeInfo(
    val id: Int,
    val plantShapeType: PlantShapeType,
    val plantShape: PlantShape,
    val drawableID: Int,
)
