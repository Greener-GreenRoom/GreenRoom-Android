package com.greener.domain.usecase.asset

import com.greener.domain.model.asset.PlantAccessoryInfo
import com.greener.domain.repository.AssetRepository
import javax.inject.Inject

class GetPlantAccessoryListUseCase @Inject constructor(
    private val repository: AssetRepository
) {
    suspend operator fun invoke(): List<PlantAccessoryInfo> =
        repository.getAllPlantAccessoryAsset()
}
