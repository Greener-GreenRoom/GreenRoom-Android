package com.greener.data.model.mypage

import com.greener.domain.model.mypage.SimpleProfile
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SimpleProfileDTO(
    @Json(name = "name") val name: String,
    @Json(name = "profileUrl") val profileUrl:String?

) {
    fun toDomain() = SimpleProfile(name,profileUrl)
}
