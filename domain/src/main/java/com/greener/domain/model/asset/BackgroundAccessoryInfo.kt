package com.greener.domain.model.asset

data class BackgroundAccessoryInfo(
    val id: Int,
    val itemType: BackgroundAccessoryType,
    val backgroundAccessory: BackgroundAccessory,
    val limitLevel: Int,
    val drawableID: Int,
)
