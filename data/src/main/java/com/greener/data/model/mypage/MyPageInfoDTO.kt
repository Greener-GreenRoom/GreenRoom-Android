package com.greener.data.model.mypage

import com.greener.domain.model.mypage.GradeTier
import com.greener.domain.model.mypage.MyPageInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyPageInfoDTO(
    @Json(name = "profile") val simpleProfileDto: SimpleProfileDTO,
    @Json(name = "gradeDto") val gradeDto: GradeDTO,
    @Json(name = "daysFromCreated") val daysFromCreated: Int,
) {
    fun toDomain() = MyPageInfo(
        simpleProfileDto.toDomain(),
        gradeDto.toDomain(),
        daysFromCreated,
        findMyTier(gradeDto.currentLevel),
    )

    private fun findMyTier(myLevel: Int): GradeTier {
        for (tier in GradeTier.entries) {
            if (tier.tierBegin <= myLevel && myLevel <= tier.tierEnd) {
                return tier
            }
        }
        return GradeTier.NONE
    }
}
