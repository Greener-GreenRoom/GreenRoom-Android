package com.greener.data.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpRequestInfo(
    @Json(name = "name") val name: String,
    @Json(name = "email") val email: String,
    @Json(name = "photoUrl") val photoUrl: String,
    @Json(name = "provider") val provider: String
)