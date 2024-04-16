package com.greener.domain.usecase.asset

import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.domain.repository.AssetRepository
import javax.inject.Inject

class GetPlantShapeListUseCase @Inject constructor(
    private val repository: AssetRepository,
) {
    suspend operator fun invoke(): List<PlantShapeInfo> =
        repository.getAllPlantShapeAsset()
}
