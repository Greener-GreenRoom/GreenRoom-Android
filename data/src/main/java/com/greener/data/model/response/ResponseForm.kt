package com.greener.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseForm<T>(
    @Json(name = "response") val response: Response,
    @Json(name = "data") val data: T?
)