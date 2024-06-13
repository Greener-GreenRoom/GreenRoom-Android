package com.greener.domain.usecase.asset

import com.greener.domain.model.asset.AssetDetailTypeInfo
import com.greener.domain.model.asset.AssetType
import com.greener.domain.repository.AssetRepository
import javax.inject.Inject

class GetAssetDetailTypeListUseCase @Inject constructor(
    private val repository: AssetRepository,
) {
    suspend operator fun invoke(assetType: AssetType): List<AssetDetailTypeInfo> =
        repository.getAssetDetailType(assetType)
}
