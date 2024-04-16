package com.greener.domain.usecase.asset

import com.greener.domain.model.asset.BackgroundAccessoryInfo
import com.greener.domain.repository.AssetRepository
import javax.inject.Inject

class GetBackgroundAccessoryListUseCase @Inject constructor(
    private val repository: AssetRepository
) {
    suspend operator fun invoke(): List<BackgroundAccessoryInfo> =
        repository.getAllBackgroundAccessoryAsset()

}
