package com.greener.data.model.greenroom.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompleteTodoList(
    @Json(name = "activityList") val activityList: List<Int>
)