package com.greener.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseListFormDTO<T>(
    @Json(name = "response") val responseDTO: ResponseDTO,
    @Json(name = "data") val data: List<T> = emptyList(),
)
