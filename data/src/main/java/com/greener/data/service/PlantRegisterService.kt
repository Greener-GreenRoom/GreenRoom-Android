package com.greener.data.service

import com.greener.data.model.plant_register.PlantInformationDTO
import com.greener.data.model.plant_register.PlantRegisterDataDTO
import com.greener.data.model.response.ResponseFormDTO
import com.greener.data.model.response.ResponseListFormDTO
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PlantRegisterService {
    @Multipart
    @POST("greenrooms")
    suspend fun registerGreenRoom(
        @Part plantRegisterRequest: MultipartBody.Part,
        @Part plantImage: MultipartBody.Part,
    ): ResponseFormDTO<PlantRegisterDataDTO>

    @GET("plants")
    suspend fun getPlantInformation(
        @Query("sort") sort: String?,
        @Query("offset") offset: Int?,
    ): ResponseListFormDTO<PlantInformationDTO>

    @GET("plants/{plantId}/watering-tip")
    suspend fun getPlantWateringTip(
        @Path("plantId") plantId: Long,
    ): ResponseFormDTO<String>

    @GET("greenrooms/duplicate?name=가롱이")
    suspend fun isDuplicateGreenRoomNickname(
        @Query("name") nickname: String,
    ): ResponseFormDTO<Boolean>
}
