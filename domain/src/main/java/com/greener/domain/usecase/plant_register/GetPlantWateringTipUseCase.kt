package com.greener.domain.usecase.plant_register

import com.greener.domain.model.plant_register.PlantInformationData
import com.greener.domain.repository.PlantRegisterRepository
import javax.inject.Inject

class GetPlantWateringTipUseCase @Inject constructor(
    private val repository: PlantRegisterRepository
) {
    suspend operator fun invoke(plantId: Long):Result<String> =
        repository.getPlantWateringTip(plantId)
}
