package com.greener.domain.model.plant_register

data class PlantInformationData(
    val plantId: Long,
    val distributionName: String,
    val plantAlias: String,
    val plantPictureUrl: String,
    val plantExplanation: String,
    val plantCategory: String,
)
