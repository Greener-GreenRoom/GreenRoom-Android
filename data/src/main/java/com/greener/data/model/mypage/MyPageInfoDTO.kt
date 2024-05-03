package com.greener.data.model.mypage

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyPageInfoDTO(
    @Json(name = "name") val name: String,
    @Json(name = "gradeDto") val gradeDto: GradeDTO,
    @Json(name = "daysFromCreated") val daysFromCreated: Int
)
