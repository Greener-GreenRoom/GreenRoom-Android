package com.greener.domain.model.asset

data class BackgroundAccessoryInfo(
    val id : Int,
    val itemType : BackgroundAccessoryType,
    val backgroundAccessoryName: BackgroundAccessoryName,
    val limitLevel: Int,
    val drawableID: Int
)
