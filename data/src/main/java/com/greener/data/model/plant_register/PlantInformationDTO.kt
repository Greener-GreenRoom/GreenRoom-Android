package com.greener.data.model.plant_register

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlantInformationDTO(
    @Json(name = "plantId") val plantId: Long,
    @Json(name = "distributionName") val distributionName: String,
    @Json(name = "plantAlias") val plantAlias: String,
    @Json(name = "plantPictureUrl") val plantPictureUrl: String,
    @Json(name = "plantExplanation") val plantExplanation: String,
    @Json(name = "plantCategory") val plantCategory: String,
)
