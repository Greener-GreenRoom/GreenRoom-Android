package com.greener.data.model.plant_register

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LevelUpDTO(
    @Json(name = "level") val level: Int,
    @Json(name = "isLevelUpdated") val isLevelUpdated: Boolean,
    @Json(name = "increasingPoint") val increasingPoint: Int,
    @Json(name = "increasingCause") val increasingCause: String?,
)
