package com.greener.presentation.model.decoration

import com.greener.domain.model.asset.AssetType

data class AssetViewItem(
    val assetType: AssetType,
    val viewObject: AssetViewObject
)
