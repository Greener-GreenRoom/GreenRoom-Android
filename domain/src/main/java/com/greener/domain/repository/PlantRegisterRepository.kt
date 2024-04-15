package com.greener.domain.repository

import com.greener.domain.model.ApiState
import com.greener.domain.model.plant_register.PlantInformationData
import com.greener.domain.model.plant_register.PlantRegisterRequestData
import com.greener.domain.model.plant_register.PlantRegisterResponseData
import java.awt.Image

interface PlantRegisterRepository {

    suspend fun registerGreenRoom(
        plantRegisterRequestData: PlantRegisterRequestData,
        image: String?
    ): Result<PlantRegisterResponseData>

    suspend fun getPlantInformation(
        sort: String?,
        offset: Int?
    ): Result<List<PlantInformationData>>

    suspend fun getPlantWateringTip(plantId: Long): Result<String>
}
