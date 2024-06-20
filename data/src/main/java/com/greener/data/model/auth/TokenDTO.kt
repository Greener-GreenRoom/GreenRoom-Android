package com.greener.data.model.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenDTO(
    @Json(name = "refreshToken") val refreshToken: String,
    @Json(name = "accessToken") val accessToken: String,
)
