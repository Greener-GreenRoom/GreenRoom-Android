package com.greener.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseDTO(
    @Json(name = "output") val output: Int,
    @Json(name = "result") val result: String
)