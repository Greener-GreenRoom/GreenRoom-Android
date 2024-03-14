package com.greener.domain.model

data class PlantInfo(
    val distributionName: String,
    val plantAlias: String,
    val plantCategory: String,
    val plantExplanation: String,
    val plantId: Int,
    val plantPictureUrl: String?
)