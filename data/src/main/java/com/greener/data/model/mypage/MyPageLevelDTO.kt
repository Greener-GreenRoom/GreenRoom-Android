package com.greener.data.model.mypage

import com.greener.domain.model.mypage.GradeTier
import com.greener.domain.model.mypage.MyLevelInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyPageLevelDTO(
    @Json(name = "gradeDto") val gradeDto: GradeDTO,
    @Json(name = "nextLevelToGetItems") val nextLevelToGetItems: Int
) {
    fun toDomain() =
        MyLevelInfo(gradeDto.toDomain(), findMyTier(gradeDto.currentLevel), nextLevelToGetItems)

    private fun findMyTier(myLevel: Int): GradeTier {
        for (tier in GradeTier.entries) {
            if (tier.tierBegin <= myLevel && myLevel <= tier.tierEnd) {
                return tier
            }
        }
        return GradeTier.NONE
    }
}