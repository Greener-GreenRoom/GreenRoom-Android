package com.greener.domain.model.asset


data class AssetDetailTypeInfo(
    val id : Int,
    val assetType: AssetType,
    val type: String,
    val typeCode : Int,
    var isChecked: Boolean = false
)
