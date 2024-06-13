package com.greener.presentation.model.decoration

import com.greener.domain.model.asset.AssetType

data class AllAssetViewItem(
    val assetType: AssetType,
    val viewObject: AllAssetViewObject
)
