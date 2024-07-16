package com.greener.domain.usecase.plant_register

import com.greener.domain.model.plant_register.PlantInformationData
import com.greener.domain.repository.PlantRegisterRepository
import javax.inject.Inject

class GetPlantInformationUseCase @Inject constructor(
    private val repository: PlantRegisterRepository,
) {
    suspend operator fun invoke(sort: String?, offset: Int?): Result<List<PlantInformationData>> =
        repository.getPlantInformation(sort, offset)
}
