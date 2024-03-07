package com.greener.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Token(
    @Json(name = "refreshToken") val refreshToken: String,
    @Json(name = "accessToken") val accessToken: String
)