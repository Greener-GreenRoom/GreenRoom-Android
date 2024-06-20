package com.greener.presentation.model.decoration

import com.greener.domain.model.asset.BackgroundAccessory
import com.greener.domain.model.asset.BackgroundAccessoryInfo
import com.greener.domain.model.asset.PlantAccessory
import com.greener.domain.model.asset.PlantAccessoryInfo
import com.greener.domain.model.asset.PlantShapeInfo

data class PlantDecorationDetailInfo(
    val shape : PlantShapeInfo? = null,
    val glasses: PlantAccessoryInfo? = null,
    val hairAccessory : PlantAccessoryInfo? = null,
    val backgroundWindow : BackgroundAccessoryInfo? = null,
    val backgroundShelf : BackgroundAccessoryInfo? = null
)
