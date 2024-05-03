package com.greener.data.model.mypage

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GradeDTO(
    @Json(name = "description") val description: String,
    @Json(name ="gradeImageUrl") val gradeImageUrl:String?,
    @Json(name = "requiredSeedToNextLevel") val requiredSeedToNextLevel:Int,
    @Json(name = "currentSeed") val currentSeed:Int,
    @Json(name = "nextLevelSeed") val nextLevelSeed:Int,
    @Json(name = "currentLevel") val currentLevel:Int
) {
    fun toDomain() {

    }
}
