package com.greener.data.model.plant_register

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlantRegisterDataDTO(
    @Json(name = "greenroomId") val greenroomId: Long,
    @Json(name = "levelUp") val levelUpDTO: LevelUpDTO,
)
