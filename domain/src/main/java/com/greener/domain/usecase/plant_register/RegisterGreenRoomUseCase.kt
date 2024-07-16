package com.greener.domain.usecase.plant_register

import com.greener.domain.model.plant_register.PlantRegisterRequestData
import com.greener.domain.model.plant_register.PlantRegisterResponseData
import com.greener.domain.repository.PlantRegisterRepository
import javax.inject.Inject

class RegisterGreenRoomUseCase @Inject constructor(
    private val repository: PlantRegisterRepository,
) {
    suspend operator fun invoke(
        plantRegisterRequestData: PlantRegisterRequestData,
        image: String?,
    ): Result<PlantRegisterResponseData> =
        repository.registerGreenRoom(plantRegisterRequestData, image)
}
