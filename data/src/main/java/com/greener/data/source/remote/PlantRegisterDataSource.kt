package com.greener.data.source.remote

import com.greener.data.model.plant_register.PlantInformationDTO
import com.greener.data.model.plant_register.PlantRegisterDataDTO
import com.greener.data.model.response.ResponseFormDTO
import com.greener.data.model.response.ResponseListFormDTO
import com.greener.data.service.PlantRegisterService
import com.greener.data.source.ResponseToApiState.toApiState
import com.greener.domain.model.ApiState
import okhttp3.MultipartBody
import javax.inject.Inject

class PlantRegisterDataSource @Inject constructor(
    private val service: PlantRegisterService
) {

    suspend fun registerGreenRoom(
        plantRegisterRequest: MultipartBody.Part,
        plantImage: MultipartBody.Part
    ): ApiState<ResponseFormDTO<PlantRegisterDataDTO>> =
        service.registerGreenRoom(plantRegisterRequest, plantImage).toApiState()

    suspend fun getPlantInformation(
        sort: String?,
        offset: Int?
    ): ApiState<ResponseListFormDTO<PlantInformationDTO>> =
        service.getPlantInformation(sort, offset).toApiState()

    suspend fun getPlantWateringTip(
        plantId: Long
    ): ApiState<ResponseFormDTO<String>> =
        service.getPlantWateringTip(plantId).toApiState()
}
