package com.greener.data.model.plant_register

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlantRegisterRequestDTO(
    @Json(name = "plantId") val plantId: Long?,
    @Json(name = "name") val name: String,
    @Json(name = "lastWatering") val lastWatering: String, // (yy-mm-dd)
    @Json(name = "wateringDuration") val wateringDuration: Int,
    @Json(name = "shape") val shape: String,
)
