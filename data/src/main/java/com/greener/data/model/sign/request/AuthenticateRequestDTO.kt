package com.greener.data.model.sign.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticateRequestDTO(
    @Json(name = "userEmail") val userEmail: String?,
    @Json(name = "provider") val provider: String?,
    @Json(name = "refreshToken") val refreshToken: String?,
    @Json(name = "accessToken") val accessToken: String,
)
