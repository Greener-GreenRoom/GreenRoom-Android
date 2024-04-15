package com.greener.domain.model.plant_register

data class LevelUpData(
    val level: Int,
    val isLevelUpdate: Boolean,
    val increasePoint: Int,
    val increaseCause: String?
)
